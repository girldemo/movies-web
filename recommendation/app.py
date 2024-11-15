from flask import Flask, request, jsonify
from flask_cors import CORS, cross_origin
from recom_system import recommend

app = Flask(__name__)
CORS(app)
app.config['CORS_HEADERS'] = 'Content-Type'

@app.route('/recommend', methods=['GET'])
@cross_origin()
def get_recommendation():
    user_id = request.args.get('user_id')
    if not user_id:
        return jsonify({'error': 'user_id is required'}), 400
    
    recommendations = recommend(int(user_id))
    return jsonify({'recommendations': recommendations})

if __name__ == '__main__':
    app.run(debug=True)