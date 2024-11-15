import {useLocation, useNavigate} from 'react-router-dom';
import Breadcrumb from 'react-bootstrap/Breadcrumb';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faHouseChimney} from '@fortawesome/free-solid-svg-icons';
import ReactPlayer from 'react-player/vimeo';
import Rating from '@mui/material/Rating';
import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';
import { parse } from '@fortawesome/fontawesome-svg-core';
import RecomendedMovie from './RecomendedMovie';

function WatchMovie({user}){
    
    const userRef = useRef(user);
    const location = useLocation();
    const param = new URLSearchParams(location.search);
    const id = param.get('id');

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [videoData, setVideoData] = useState(null);
    const [movieData, setMovieData] = useState(null);
    const [value, setValue] = useState(null);
    const [isRated, setIsRated] = useState(false);

    function getCurrentDate() {
        const date = new Date();
        const year = date.getFullYear();
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const day = String(date.getDate()).padStart(2, '0');
        return `${year}-${month}-${day}`;
    }
    
    useEffect(() => {
        const fetchData = async () => {
            try{
                const response1 =  await axios.get(`http://localhost:8080/api/v1/movies/videos/${id}`);
                const response2 =  await axios.get(`http://localhost:8080/api/v1/movies/${id}`);
                const response3 = await axios.get(`http://localhost:8080/api/v1/user/rating?movieId=${id}&email=${user}`);
                setVideoData(response1.data);
                setMovieData(response2.data);
                if (response3.data) {
                    setValue(response3.data.stars);
                    setIsRated(true);
                }
                setLoading(false);
            } catch (error){
                setError(error);
                setLoading(false);
            }
        };
        fetchData();
    }, [user]);

    // console.log(videoData);

    const handleRatingClick = async (newValue) => {
        console.log(newValue);
        setValue(newValue);
        try {
            if (isRated) {
                await axios.put(`http://localhost:8080/api/v1/user/rating?movieId=${id}&email=${user}`, {
                    stars: newValue,
                    ratingDate: getCurrentDate()
                });
            }
            else {
                await axios.post(`http://localhost:8080/api/v1/user/rating?movieId=${id}&email=${user}`, {
                    stars: newValue,
                    ratingDate: getCurrentDate()
                });
                setIsRated(true);
            }
        }
        catch (error) {
            setError(error);
            setLoading(false);
        }
    };

    const handleClick = (event) => {
        const newValue = event.target.getAttribute('value');
        if (newValue === null || newValue === 0) {
            return;
        }
        handleRatingClick(newValue);
    };

    if (loading) return <h1>Loading Data...</h1>
    if (error) return <h3>Error: {error.message}</h3>

    return (
        <>
        <div className='watch-movie-container'>
            {/* <h1>{videoData.id}</h1> */}
            <Breadcrumb>
                <Breadcrumb.Item href="/" className='breadcrumb-item'>
                    <FontAwesomeIcon icon={faHouseChimney} /> Trang chủ
                </Breadcrumb.Item> 
                <Breadcrumb.Item href={`/detail?id=${id}`} className='breadcrumb-item'>
                    <span>{movieData[0].title}</span>
                </Breadcrumb.Item>
                <Breadcrumb.Item active className='breadcrumb-item'>
                    <span className='breadcrumb-item-detail-movie'>Xem phim</span>
                </Breadcrumb.Item>
            </Breadcrumb>

            <div className='video-player'>
                <ReactPlayer url={videoData.videoUrl} controls={true}/>
            </div>
            <div className='rating-movie'>
                <h4>Đánh giá phim</h4>
                {user ? 
                (
                        <Rating
                        name="half-rating" value={value} precision={1}
                        onClick={handleClick}
                        />
                ) : 
                (
                    <p>Bạn phải <a href='/login'>đăng nhập</a> để đánh giá phim</p>
                )}
                
            </div>
        </div>
        <RecomendedMovie user={user}/>
        </>
    );
}

export default WatchMovie