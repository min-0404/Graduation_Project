import pymysql
import pandas as pd
from flask import Flask, jsonify, request
from sklearn.metrics.pairwise import cosine_similarity
from flask_restful import Api, Resource

from flask_cors import CORS


# example = '''SELECT * FROM cardvisor_beta3.serviceOne;'''

# DB 연결
def db_connector(sql):
    db = pymysql.connect(
        host='cardvisor.cebijewyqmq6.ap-northeast-2.rds.amazonaws.com',
        passwd='graduation2022',
        db='cardvisor_rds_beta1',

#         host='localhost',
#         passwd='root',
#         db='cardvisor_beta3',

        port=3306,
        user='root',
        charset='utf8',
        autocommit=True,
        cursorclass=pymysql.cursors.DictCursor
    )
    cursor = db.cursor()
    cursor.execute(sql)
    result = cursor.fetchall()
    db.close()
    return result

# 두 리스트에서 중복되지 않는 elememnt 추출 (사용자가 브랜드를 선택했지만 해당 브랜드 혜택을 가지고있는 카드가 존재하지 않을 경우)
def non_match_elements(list_a, list_b):
    non_match = []
    for i in list_a:
        if i not in list_b:
            non_match.append(i)
    return non_match


app = Flask(__name__)
cors = CORS(app, resources={r'/api/*': {'origins': 'https://localhost:8080'}})

api = Api(app)


# @api.route('/motion/<string:word>', methods=['GET'])
# class motion(Resource):
#     def get(self, word):

#         word = unquote_plus(word)
#         combined = join_jamos(word)

#         return {'result' : "%s" % combined}



class serviceone(Resource):
    def get(self, spring_member_id):
#         sql = "USE cardvisor_beta3;"
        sql = "USE cardvisor_rds_beta1;"
        result = db_connector(sql)

        sql = "SELECT * FROM serviceone where member_id = {};".format(spring_member_id)
        result = db_connector(sql)
        df = pd.DataFrame(result)

        # dataframe에서 'serviceone_id' 제거
        df = df.drop(columns=['serviceone_id'])

        # 'brand_id'값을 OneHot Encoding 하여 각 id를 칼럼화
        brand_dummies = pd.get_dummies(df.brand_id)
        df = pd.concat([df, brand_dummies], axis = 'columns')

        # 선택된 브랜드 아이들을 따로 저장
        brands1 = df.brand_id

        # OneHot Encoding을 마쳤으니 'brand_id' 칼럼 제거
        df = df.drop(columns=['brand_id'])

        # 'member_id' 칼럼의 값이 전부 1 이므로 해당 칼럼의 value에 맞춰 통일 => 이때 각 'brand_id' 칼럼값들은 sum()
        # 결과 => member_id : 1, brand_id1 : 1, brand_id2 : 1 , ..... , brand_id(n) : 1
        df = df.groupby(['member_id'], as_index=False).sum()


        # 브랜드에 대한 혜택이 존재하지 않는 카드가 있을 수 있으니 아직 완성된것은 아니므로 members_choice 변수로 저장해놓음
        members_choice = df.copy()


        # 6000개의 혜택중에 사용자가 선택한 'brand_id'값을 가지고 있는 혜택에서 'card_code'와 'brand_id' 추출
        sql = f"""
        SELECT card_code, brand_id FROM benefit
        WHERE brand_id in {tuple(brands1)}
        """
        options = db_connector(sql)
        df = pd.DataFrame(options)


        # 사용자가 선택한 'brand_id' 중에서 해당 유효성 있는?(1개의 카드라도 해당 혜택 포함) 'brand_id' 값들을 brands2 변수에 저장
        brands2 = df.brand_id

        # brands1 과 brands2를 비교하여 혜택이 존재하지 않는 'brand_id'를 추출
        trash = non_match_elements(list(brands1), list(brands2))

        # 추출된 'brand_id' 값 칼럼을 members_choice에서 제거
        for col in trash:
            members_choice = members_choice.drop(columns=col)

        members_choice = members_choice.loc[:, members_choice.columns != 'member_id'].astype('bool')
        members_choice = members_choice.loc[:, members_choice.columns != 'member_id'].astype('int')



        # 'card_code', 'brand_id' dataframe에서 'brand_id' value들을 OneHot Encoding 하여 칼럼화
        brand_dummies = pd.get_dummies(df.brand_id)
        df = pd.concat([df, brand_dummies], axis = 'columns')

        # 'brand_id' 칼럼 제거
        df = df.drop(columns=['brand_id'])

        # 'member_id' 칼럼의 값이 전부 1 이므로 해당 칼럼의 value에 맞춰 통일 => 이때 각 'brand_id' 칼럼값들은 sum()
        # 하지만 이때 'card_code'와 'brand' 값이 완전히 중복되는 row들이 있으므로 혜택이 없으면 0이지만 있으면 무조건 1이 아님
        df = df.groupby(['card_code'], as_index=False).sum()

        # int -> bool -> int datatype 변환을 거쳐 숫자가 존재하면 전부 1로 교체
        temp = df.loc[:, df.columns != 'card_code'].astype('bool')
        temp = temp.loc[:, temp.columns != 'card_code'].astype('int')
        df = pd.concat([df['card_code'], temp], axis='columns')

        recommendable_cards = df.copy()


        # 코사인 유사도 계산
        final = pd.DataFrame(cosine_similarity(
            members_choice.loc[:, members_choice.columns != 'member_id'],
            recommendable_cards.loc[:, recommendable_cards.columns != 'card_code']
            ),
            columns = list(recommendable_cards.card_code), index = ['similarity']
            )

        #칼럼이 데이터를 더 가공하기 쉬우므로 transpose
        final = final.transpose()

        final = final.sort_values(by=['similarity'], ascending=False)
        final = final.head(20)

        # 유사도 높은 상위 10개 항목의 'card_code' 값을 리스트로 저장 후 출력
        final_cards = list(final.index.values)
        final_cards = list([int(x) for x in final_cards])

        cardList = { "cards" : final_cards }

        sql = "DELETE FROM serviceone where member_id = {};".format(spring_member_id)
        result = db_connector(sql)
        df = pd.DataFrame(result)


        # 해당 리스트를 브라우저 화면에 출력
#         print(cardList)
        return jsonify(cardList)

class servicetwo(Resource):
    def get(self, spring_member_id):
        sql = "USE cardvisor_rds_beta1;"
        result = db_connector(sql)

        sql = "SELECT * FROM servicetwo where member_id != {};".format(spring_member_id)
        result = db_connector(sql)
        df = pd.DataFrame(result)
        df = df.drop(columns=['servicetwo_id'])
        category_dummies = pd.get_dummies(df.category_id)
        df = pd.concat([df, category_dummies], axis = 'columns')
        brands1 = df.category_id
        df = df.drop(columns=['category_id'])
        idList = df['member_id']
        df = df.loc[:, ~df.columns.isin(['cost', 'member_id'])].mul(df[df.columns[0]].values.tolist(), axis=0)
        df.insert(0, 'member_id', idList)
        df = df.groupby(['member_id'], as_index=False).sum()
        vector1 = df.copy()

        sql = "SELECT * FROM servicetwo where member_id = {};".format(spring_member_id)
        result = db_connector(sql)
        df = pd.DataFrame(result)
        df = df.drop(columns=['servicetwo_id'])
        category_dummies = pd.get_dummies(df.category_id)
        df = pd.concat([df, category_dummies], axis = 'columns')
        brands1 = df.category_id
        df = df.drop(columns=['category_id'])
        idList = df['member_id']
        df = df.loc[:, ~df.columns.isin(['cost', 'member_id'])].mul(df[df.columns[0]].values.tolist(), axis=0)
        df.insert(0, 'member_id', idList)
        df = df.groupby(['member_id'], as_index=False).sum()
        vector2 = df.copy()

        final = pd.DataFrame(cosine_similarity(
        vector2.loc[:, vector2.columns != 'member_id'],
        vector1.loc[:, vector1.columns != 'member_id']
            ),
        columns = list(vector1.member_id), index = ['similarity']
        )
        final = final.transpose()
        final = final.sort_values(by=['similarity'], ascending=False)
        final = final.head(1)
        best_choice = final.index.values[0]

        sql = "SELECT * FROM cold_start where member_id = {};".format(best_choice)
        result = db_connector(sql)
        df = pd.DataFrame(result)
        cards = df['card_code']
        cards = list(cards)

        cardList = { "cards" : cards }

        return jsonify(cardList)




api.add_resource(serviceone, "/api/serviceone/<int:spring_member_id>")
api.add_resource(servicetwo, "/api/servicetwo/<int:spring_member_id>")


if __name__ == "__main__":
    app.run(host='127.0.0.1', port="5001", debug=True)