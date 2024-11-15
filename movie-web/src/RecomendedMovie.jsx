import Slider from "react-slick";
import "slick-carousel/slick/slick.css";
import "slick-carousel/slick/slick-theme.css";
import React, {useState, useEffect, useRef} from 'react';
import axios from 'axios';
import { Link } from "react-router-dom";

function RecomendedMovie({user}){

    const [userId, setUserId] = useState(null);
    const [movieIdList, setMovieIdList] = useState(null);
    const [movieData, setMovieData] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
      if (user !== null) {
        const fetchUserId = async () => {
          try{
              const response =  await axios.get(`http://localhost:8080/api/v1/user/${user}`);
              setUserId(response.data.id);
              setLoading(false);
          } catch (error){
            setError(error);
            setLoading(false);
          }
        };

        fetchUserId();
      }
    }, [user]);
    
    useEffect(() => {
      if (userId !== null) {
        const fetchMovieIds = async () => {
            try{
                const response1 =  await axios.get(`http://127.0.0.1:5000/recommend?user_id=${userId}`);
                setMovieIdList(response1.data.recommendations);
                setLoading(false);
            } catch (error){
              setError(error);
              setLoading(false);
            }
        };
        
        fetchMovieIds();
      }
    }, [userId]);

    useEffect(() => {
      if (movieIdList !== null) {
        const fetchMovieDatas = async () => {
            try{
              const response2 = await axios.post(`http://localhost:8080/api/v1/movies/by-ids`, 
                movieIdList.slice(0, 6)
              );
              setMovieData(response2.data); 
              setLoading(false);
            } catch (error){
              setError(error);
              setLoading(false);
            }
        };
    
        fetchMovieDatas();
      }
    }, [movieIdList]);

    useEffect(() => {
      if (user === null) {
        const fetchMovieDatas = async () => {
            try{
              const response2 = await axios.post(`http://localhost:8080/api/v1/movies/by-ids`, 
                [17, 18, 19, 23, 21, 22]
              );
              setMovieData(response2.data); 
              setLoading(false);
            } catch (error){
              setError(error);
              setLoading(false);
            }
        };
    
        fetchMovieDatas();
      }
    }, [user]);

    const settings = {
      dots: true,
      infinite: true,
      speed: 500,
      slidesToShow: 6,
      slidesToScroll: 6,
      initialSlide: 0,
      responsive: [
        {
          breakpoint: 1024,
          settings: {
            slidesToShow: 3,
            slidesToScroll: 3,
            infinite: true,
            dots: true
          }
        },
        {
          breakpoint: 600,
          settings: {
            slidesToShow: 2,
            slidesToScroll: 2,
            initialSlide: 2
          }
        },
        {
          breakpoint: 480,
          settings: {
            slidesToShow: 1,
            slidesToScroll: 1
          }
        }
      ]
    };
    
    if (loading) return <h1>Loading Data...</h1>
    if (error) return <h3>Error: {error.message}</h3>

    return(
        <div className="recomended-movie-list">
            <h1 className="recomended-movie-title">PHIM ĐỀ CỬ</h1>
            <Slider {...settings}>
                {movieData && movieData.map((item) => (
                    <div className="recomended-movie-container">
                      <Link to={`/detail?id=${item.id}`} key={item.id}>
                        <img src={item.posterUrl} alt="movie image"/>
                        <p>{item.title}</p>
                      </Link>
                    </div>

                ))}
            </Slider>
        </div>
    );
}

export default RecomendedMovie;