import 'bootstrap/dist/css/bootstrap.min.css';
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import {Link, useParams} from 'react-router-dom';
import Pagination from 'react-bootstrap/Pagination';
import PageItem from 'react-bootstrap/PageItem'
import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';
import HeaderMovieList from './HeaderMovieList';

function MovieList(props){
    
    const {searchKey} = useParams();
    let items = [];
    const pageRef = useRef(0);
    const [pageNumber, setpageNumber] = useState(0);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [moviesData, setMoviesData] = useState(null);

    useEffect(() => {
        const fetchGenresData = async () => {
            try{
                let response1 = null;
                if (searchKey !== null && searchKey !== undefined)
                    response1 =  await axios.get(`http://localhost:8080/api/v1/movies/search?${searchKey}&pageNumber=${pageNumber}&pageSize=10`);
                else
                    response1 =  await axios.get(`http://localhost:8080/api/v1/movies/${props.keyword}?pageNumber=${pageNumber}&pageSize=10`);
                setMoviesData(response1.data.data);
                pageRef.current = response1.data.totalPages;
                setLoading(false);
            } catch (error){
                setError(error);
            }
        };

        fetchGenresData();

    }, [pageNumber, props.keyword, searchKey]);

    if (loading) return <h1>Loading Data...</h1>
    if (error) return <h3>Error: {error.message}</h3>

    const handleClick = (number) => {
        setpageNumber(p => p = number-1);
    }

    for (let number = 1; number<=pageRef.current; number++) {
        items.push(
          <Pagination.Item key={number} active={number === (pageNumber+1)} onClick={() => handleClick(number)}>
            {number}
          </Pagination.Item>,
        );
    }
 
    // const firstRow = data.slice(0, 5);
    // const secondRow = data.slice(5, 10);
    // console.log(pageRef.current);  
    return(
        <>
        <div className="updated-movie-container">
            {/* <h1>{searchKey}</h1> */}
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
            <div className='pagination'>
            <Pagination size='sm'>
               {items}
            </Pagination>
            </div>
        </div>
        </>
    );
}

export default MovieList