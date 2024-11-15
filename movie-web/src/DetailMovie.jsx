import {useLocation, useNavigate} from 'react-router-dom';
import Breadcrumb from 'react-bootstrap/Breadcrumb';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faHouseChimney} from '@fortawesome/free-solid-svg-icons';
import {BsPlayBtnFill} from 'react-icons/bs';
import { IoIosPlayCircle } from "react-icons/io";
import Col from 'react-bootstrap/Col';
import Row from 'react-bootstrap/Row';
import 'bootstrap/dist/css/bootstrap.min.css';
import Rating from '@mui/material/Rating';
import ReactPlayer from 'react-player/youtube';
import Comments from './comments/Comments';
import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';

function DetailMovie({user}){

    const location = useLocation();
    const param = new URLSearchParams(location.search);
    const id = param.get('id');

    const navigate = useNavigate();
    const clickButtonHandler = () => {
        navigate(`/watch?id=${id}`);
    }
    
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [movieData, setMovieData] = useState(null);
    const [value, setValue] = useState(null);

    useEffect(() => {
        const fetchGenresData = async () => {
            try{
                const response1 =  await axios.get(`http://localhost:8080/api/v1/movies/${id}`);
                setMovieData(response1.data);
                setLoading(false);
            } catch (error){
                setError(error);
                setLoading(false);
            }
        };

        fetchGenresData();
    }, []);

    useEffect(() => {
        if (user !== null) {
            const fetchGenresData = async () => {
                try{
                    const response2 = await axios.get(`http://localhost:8080/api/v1/user/rating?movieId=${id}&email=${user}`);
                    setValue(response2.data.stars);
                    setLoading(false);
                } catch (error){
                    setError(error);
                    setLoading(false);
                }
            };

            fetchGenresData();
        }
    }, [user]);

    if (loading) return <h1>Loading Data...</h1>
    if (error) return <h3>Error: {error.message}</h3>
    // console.log(movieData);

    return (
        <div className='detail-movie-container'>
            <Breadcrumb>
                <Breadcrumb.Item href="/" className='breadcrumb-item'>
                    <FontAwesomeIcon icon={faHouseChimney} /> Trang chủ
                </Breadcrumb.Item>
                <Breadcrumb.Item active className='breadcrumb-item'>
                    <span className='breadcrumb-item-detail-movie'>{movieData && movieData[0].title}</span>
                </Breadcrumb.Item>
            </Breadcrumb>

            <div className='detail-movie'>
                <Row>
                <Col xs={4}>
                    <img src={movieData && movieData[0].posterUrl} alt='movie image'/>
                    <div className='button-container'>
                        <button className='trailer-button'><BsPlayBtnFill/> Trailer</button>
                        <button className='play-button' onClick={clickButtonHandler}><IoIosPlayCircle />Xem phim</button>
                    </div>
                </Col>
                <Col>
                    <div className='detail-movie-content'>
                        <h1>{movieData && movieData[0].title}</h1>
                        <p>Thể loại: <span className='test'>{movieData && movieData[1].replace(/,/g, ', ')}</span></p>
                        <p>Diễn viên: {movieData && movieData[3].replace(/,/g, ', ')}</p>
                        <p>Năm phát hành: {movieData && movieData[0].releaseYear.slice(0, 4)}</p>
                        <p>Đạo diễn: {movieData && movieData[2].replace(/,/g, ', ')}</p>
                        <p>Quốc gia: {movieData && movieData[0].language}</p>
                        <p>Thời lượng: {Math.floor(movieData && movieData[0].duration/60)} giờ {movieData && movieData[0].duration%60} phút</p>
                        <p>Điểm Imdp: {movieData && movieData[0].imdpScore}</p>
                        {user ?
                        (
                            <Rating
                            name="read-only"
                            value={value}   
                            readOnly
                        />
                        ) :
                        (
                            <p><a href='/login'>Đăng nhập</a> để xem đánh giá của bạn dành cho bộ phim này</p>
                        )}
                    </div>
                </Col>
                </Row>
                <Row>
                    <div className='detail-movie-description'>
                        <h3>Nội dung phim</h3>
                        <p>{movieData && movieData[0].description}</p>
                    </div>
                </Row>
                <div>
                    <ReactPlayer url={movieData && movieData[0].trailerUrl} controls={true}/>
                </div>
            </div>
        </div>
    );
}

export default DetailMovie