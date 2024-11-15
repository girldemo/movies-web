import {useParams, Link, useNavigate} from 'react-router-dom';
import Form from 'react-bootstrap/Form';
import 'bootstrap/dist/css/bootstrap.min.css';
import Breadcrumb from 'react-bootstrap/Breadcrumb';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {faHouseChimney} from '@fortawesome/free-solid-svg-icons';
import MovieList from './MovieList';
import React, {useState, useEffect, createContext, useRef} from 'react';
import axios from 'axios';

function HeaderMovieList(){

    const searchRef = useRef(null);
    const navigate = useNavigate();
    const {genreName} = useParams();
    const {genreId} = useParams();
    const {nationName} = useParams();
    const {year} = useParams();
    const {keyword} = useParams();
    const {movieName} = useParams();

    const [searchKey, setSearchKey] = useState("");
    let final_keyword = "";
    if (genreName!==null && genreName!==undefined){
        final_keyword = `genres/${genreId}`;
    }
    else if (nationName!==null && nationName!==undefined){
        final_keyword = `nations/${nationName}`;
    }
    else if (year!==null && year!=undefined){
        final_keyword = `years/${year}`;
    }
    else if (movieName!==null && movieName!==undefined){
        final_keyword = `title/${movieName}`;
    } 
    else if (keyword!==null && keyword!==undefined){
        if (keyword === "title")
            final_keyword = `title/ `;
        else 
            final_keyword = keyword;
    }

    const [genre, setGenre] = useState('');
    const [nation, setNation] = useState('');
    const [releaseYear, setReleaseYear] = useState('');
    const [sortField, setSortField] = useState('id');
    const [asc, setAsc] = useState(true);


    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [genresData, setGenresData] = useState(null);
    const [yearsAndNationsData, setYearsAndNationsData] = useState(null);

    useEffect(() => {
        const fetchGenresData = async () => {
            try{
                const response1 =  await axios.get('http://localhost:8080/api/v1/genres');
                setGenresData(response1.data);
                const response2 = await axios.get('http://localhost:8080/api/v1/movies/datesAndNations');
                setYearsAndNationsData(response2.data);
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

    const years = (Array.from(new Set(yearsAndNationsData.map(item => item[0]))));
    const nations = Array.from(new Set(yearsAndNationsData.map(item => item[1])));
    //sort
    const yearsList = years.sort((a, b) => a - b);
    const nationsList = nations.sort((a, b) => a.localeCompare(b));

    const handleSubmit = () => {
        const filteredParams = Object.fromEntries(
            Object.entries({genre, nation, releaseYear, sortField, asc}).filter(([_, v]) => v !== '')
        );
        // setSearchKey(sk => sk = new URLSearchParams({genre, nation, releaseYear, sortField, asc}).toString());
        searchRef.current = new URLSearchParams(filteredParams).toString();
        navigate(`/movies/search/${searchRef.current}`);
    };

    return (
        <>
        {/* <h1>{searchKey}</h1> */}
        <div className='header-movie-list-container'>
            <h2>{genreName}{nationName}{year}{keyword}{movieName}</h2>
            <div className='advance-search'>
                <Form.Select aria-label="Thể loại" value={genre} onChange={(e) => setGenre(e.target.value)}>
                    <option>Thể loại</option>
                    {genresData && genresData.map(genre => (
                        <option key={genre.id} value={genre.genreName}>{genre.genreName}</option>
                    ))}
                </Form.Select>
                <Form.Select aria-label='Quốc gia' value={nation} onChange={(e) => setNation(e.target.value)}>
                    <option>Quốc gia</option>
                    {nationsList && nationsList.map((nation, index) => (
                        <option key={index} value={nation}>{nation}</option>
                    ))}
                </Form.Select>
                <Form.Select aria-label='Năm phát hành' value={releaseYear} onChange={(e) => setReleaseYear(e.target.value)}>
                    <option>Năm phát hành</option>
                    {yearsList && yearsList.map((year, index) => (
                        <option key={index} value={year}>{year}</option>
                    ))}
                </Form.Select>
                <Form.Select aria-label='Sắp xếp' value={sortField} onChange={(e) => setSortField(e.target.value)}>
                    <option>Sắp xếp theo</option>
                    <option value="title">Tên phim</option>
                    <option value="imdbScore">Điểm Imbd</option>
                </Form.Select>
                <Form.Select aria-label='Thứ tự' value={asc} onChange={(e) => setAsc(e.target.value === "true")}>
                    <option>Thứ tự</option>
                    <option value="true">A-{'>'}Z</option>
                    <option value="false">Z-{'>'}A</option>
                </Form.Select>
                <button onClick={handleSubmit}>Tìm kiếm</button>
            </div>
            <div className='header-movie-list-title'>
                <Breadcrumb>
                    <Breadcrumb.Item href="/" className='breadcrumb-item'>
                        <FontAwesomeIcon icon={faHouseChimney} /> Trang chủ
                    </Breadcrumb.Item>
                    <Breadcrumb.Item active className='breadcrumb-item'>
                        <span className='breadcrumb-item-detail-movie'>{genreName}{nationName}{year}{keyword}{movieName}</span>
                    </Breadcrumb.Item>
                </Breadcrumb>
                <p>
                Khám phá danh sách Phim Hành Động mới nhất và hấp dẫn, cập nhật liên tục trên Movie. 
                Xem hơn 100.000+ bộ phim hành động Vietsub, thuyết minh đang thịnh hành và hay nhất năm 2024.
                </p>
            </div>
        </div>
        <MovieList keyword={final_keyword}/>
        </>
    );
}

export default HeaderMovieList