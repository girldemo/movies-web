import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import 'bootstrap/dist/css/bootstrap.min.css';
import {Link, useNavigate} from 'react-router-dom';
import React, {useState, useEffect} from 'react';
import axios from 'axios';

function NewMovie() {
    
    const navigate = useNavigate();

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [moviesData, setMoviesData] = useState(null);

    useEffect(() => {
        const fetchGenresData = async () => {
            try{
                const response1 =  await axios.get('http://localhost:8080/api/v1/movies/created?pageNumber=0&pageSize=10');
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
        <div className="new-movie-container">
            <Container className='new-movie-header'>
                <Row>
                    <Col xs={4}>
                        <h1 className="new-movie-title">PHIM CHIẾU RẠP MỚI</h1>
                    </Col>
                    <Col className='tag' xs={4}>
                        <label><a href='/years/2024'>2024</a></label>
                        <label><a href='/years/2023'>2023</a></label>
                        <label><a href='/years/2022'>2022</a></label>
                        <label><a href='/years/2021'>2021</a></label>
                    </Col>
                    <Col>
                        <button className='more-button' onClick={() => navigate('/movies/created')}>Xem tất cả ▶</button>
                    </Col>
                </Row>
            </Container>
            <Container className='new-movie-list'>
                <Row xs="auto">
                    {moviesData.slice(0, 5).map((item) => (
                        <Link to={`/detail?id=${item.id}`} key={item.id}>
                            <Col className="new-movie-row"> 
                                <img src={item.posterUrl} alt="movie image"/>
                                <p>{item.title}</p>
                            </Col>
                        </Link>
                    ))}
                </Row>
                <Row xs="auto">   
                    {moviesData.slice(5, 10).map((item) => (
                        <Link to={`/detail?id=${item.id}`} key={item.id}>
                            <Col className="new-movie-row"> 
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

export default NewMovie