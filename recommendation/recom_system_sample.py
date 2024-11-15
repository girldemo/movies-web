import mysql.connector
import pandas as pd
import numpy as np
from sklearn.feature_extraction.text import TfidfTransformer
from sklearn.linear_model import Ridge
from sklearn import linear_model

def recommend(n_user):
    # Thiết lập kết nối
    mydb = mysql.connector.connect(
    host="",
    user="",
    password="",
    database=""
    )

    # Kiểm tra kết nối
    if mydb.is_connected():
        print("Kết nối thành công!")

    # Thực thi các truy vấn SQL
    mycursor = mydb.cursor()

    # Truy vấn dữ liệu
    # Lấy kết quả
    mycursor.execute("SELECT account_id, movie_id, stars FROM ratings")
    results = mycursor.fetchall()

    mycursor.execute("select m.id, group_concat(g.genre_name separator ', ') as genres from movies m join movies_has_genres mg on m.id = mg.movie_id join genres g on g.id = mg.genre_id group by m.id;")
    results2 = mycursor.fetchall()

    mycursor.execute("select distinct account_id from ratings;")
    results3 = mycursor.fetchall()

    # Đóng kết nối
    mycursor.close()
    mydb.close()


    # Hàm chuyển đổi từ tuple thành chuỗi theo định dạng bạn mong muốn
    def format_result(result):
        return '|'.join(map(str, result))

    # Mở một file để ghi dữ liệu
    with open('user_rating.txt', 'w') as file:
        # Ghi từng dòng đã được định dạng vào file
        for result in results:
            file.write(format_result(result) + '\n')

    print("Ghi file thành công!")

    #Reading ratings file:
    r_cols = ['user_id', 'movie_id', 'rating']

    ratings_base = pd.read_csv('user_rating.txt', sep='|', names=r_cols, encoding='latin-1')

    id = -1
    movies = [(movie_id, genres, id:=id+1) for movie_id, genres in results2]
    movies_df = pd.DataFrame(movies, columns=['movie_id', 'genres', 'id'])
    merged_df = pd.merge(ratings_base, movies_df[['movie_id', 'id']], on='movie_id', how='left')

    id = -1
    results3_list = [item[0] for item in results3]
    users = [(u_id, id:=id+1) for u_id in results3_list]
    users_df = pd.DataFrame(users, columns=['user_id', 'u_id'])
    merged_df2 = pd.merge(merged_df, users_df[['user_id', 'u_id']], on='user_id', how='left')
    rate_train = merged_df2.values
    print('Number of traing rates:', rate_train.shape[0])

    #--------------------------------
    if (n_user not in results3_list):
        return [11, 12, 13, 14, 15, 16]
    #-------------------------------

    genres = ['unknown', 'Action', 'Adventure',
    'Animation', 'Children\'s', 'Comedy', 'Crime', 'Documentary', 'Drama', 'Fantasy',
    'Film-Noir', 'Horror', 'Musical', 'Mystery', 'Romance', 'Sci-Fi', 'Thriller', 'War', 'Western']
    genres_tfidf = []
    for result2 in results2:
        # Chuyển đổi chuỗi thành danh sách các thể loại
        movie_genres_str = result2[1]
        movie_genres = movie_genres_str.split(", ")

        # Tạo danh sách nhị phân dựa trên thể loại phim
        binary_representation = [1 if genre in movie_genres else 0 for genre in genres]
        genres_tfidf.append(binary_representation)

    #tfidf
    from sklearn.feature_extraction.text import TfidfTransformer
    transformer = TfidfTransformer(smooth_idf=True, norm ='l2')
    tfidf = transformer.fit_transform(genres_tfidf).toarray()
    # print(tfidf)

    def get_items_rated_by_user(rate_matrix, user_id):
        """
        return (item_ids, scores)
        """
        y = rate_matrix[:,4] # all users
        # item indices rated by user_id
        # we need to +1 to user_id since in the rate_matrix, id starts from 1
        # but id in python starts from 0
        ids = np.where(y == user_id)[0]
        item_ids = rate_matrix[ids, 3]# index starts from 0
        scores = rate_matrix[ids, 2]
        return (item_ids, scores)

    n_users = len(results3)
    d = tfidf.shape[1] # data dimension
    W = np.zeros((d, n_users))
    b = np.zeros((1, n_users))

    for n in range(n_users):
        ids, scores = get_items_rated_by_user(rate_train, n)
        clf = Ridge(alpha=0.01, fit_intercept  = True)
        Xhat = tfidf[ids, :]

        clf.fit(Xhat, scores)
        W[:, n] = clf.coef_
        b[0, n] = clf.intercept_

    n_user_index = results3_list.index(n_user)

    Yhat = tfidf.dot(W) + b
    response = np.array(Yhat[:, n_user_index])
    sorted_response = np.argsort(-response)
    recommend = [(i, response[i]) for i in sorted_response]

    ids, scores = get_items_rated_by_user(rate_train, n_user_index)
    remove_ids_rated = set(ids)
    remove_ids_rated
    filtered_recommend = [item for item in recommend if item[0] not in remove_ids_rated]

    #ket qua cuoi cung la id cua cac phim duoc goi y
    final_result = []
    for item in filtered_recommend:
        result = results2[item[0]][0]
        final_result.append(result)
    print(final_result)

    return final_result

    # from math import sqrt
    # def evaluate(Yhat, rates, W, b):
    #     se = 0
    #     cnt = 0
    #     for n in range(n_users):
    #         ids, scores_truth = get_items_rated_by_user(rates, n)
    #         scores_pred = Yhat[ids, n]
    #         e = scores_truth - scores_pred
    #         se += (e*e).sum(axis = 0)
    #         cnt += e.size
    #     return sqrt(se/cnt)

    # print('RMSE:', evaluate(Yhat, rate_train, W, b))

