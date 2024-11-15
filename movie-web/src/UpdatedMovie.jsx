import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Link, useNavigate} from 'react-router-dom';
import React, {useState, useEffect} from 'react';
import axios from 'axios';

function UpdatedMovie(){

    const navigate = useNavigate();

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [moviesData, setMoviesData] = useState(null);

    useEffect(() => {
        const fetchGenresData = async () => {
            try{
                const response1 =  await axios.get('http://localhost:8080/api/v1/movies/updated?pageNumber=0&pageSize=10');
                setMoviesData(response1.data.data);
                setLoading(false);
            } catch (error){
                setError(error);
                setLoading(false);
            }
        };

        fetchGenresData();

    }, []);

    if (loading) return <h1>Loading Data...</h1>
    if (error) return <h3>Error: {error.message}</h3>

    return (
        <div className="updated-movie-container">
            <Container className='updated-movie-header'>
                <Row>
                    <Col xs={4}>
                        <h1 className="updated-movie-title">PHIM MỚI CẬP NHẬT</h1>
                    </Col>
                    <Col className='tag' xs={4}>
                        <label><a href='/genres/2/Action'>Hành động</a></label>
                        <label><a href='/genres/4/Animation'>Hoạt hình</a></label>
                        <label><a href='/genres/17/Horror'>Kinh dị</a></label>
                        <label><a href='/genres/6/Comedy'>Hài hước</a></label>
                    </Col>
                    <Col>
                        <button className='more-button' onClick={() => navigate('/movies/updated')}>Xem tất cả ▶</button>
                    </Col>
                </Row>
            </Container>
            <Container className='updated-movie-list'>
                <Row xs="auto">
                    {moviesData.slice(0, 5).map((item) => (
                        <Link to={`/detail?id=${item.id}`} key={item.id}>
                            <Col className="updated-movie-row"> 
                                <img src={item.posterUrl} alt="movie image"/>
                                <p>{item.title}</p>
                            </Col>
                        </Link>
                    ))}
                </Row>
                <Row xs="auto">   
                    {moviesData.slice(5, 10).map((item) => (
                        <Link to={`/detail?id=${item.id}`} key={item.id}>
                            <Col className="updated-movie-row"> 
                                <img src={item.posterUrl} alt="movie image"/>
                                <p>{item.title}</p>
                            </Col>
                        </Link>
                    ))}
                </Row>
            </Container>
        </div>
    );
}

export default UpdatedMovie