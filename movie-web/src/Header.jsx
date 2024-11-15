import logo from './assets/roll-film.png';
import {Link, useNavigate} from 'react-router-dom';
import React, {useState, useEffect, createContext} from 'react';
import axios from 'axios';
import Dropdown from 'react-bootstrap/Dropdown';
import DropdownButton from 'react-bootstrap/DropdownButton';

function Header({user, setUser}){

    const navigate = useNavigate();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [genresData, setGenresData] = useState(null);
    const [yearsAndNationsData, setYearsAndNationsData] = useState(null);
    const [searchName, setSearchName] = useState("");

    const handleClick = () => {
        navigate(`/login`);
    };

    const handleSubmit = () => {
        if (searchName.trim() === '')
            return;
        navigate(`/movies/title/${searchName}`);
    };

    const handleLogout = () => {
        setUser(null);
        navigate("/");
    };

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
    // console.log(yearsAndNationsData);
    const years = (Array.from(new Set(yearsAndNationsData.map(item => item[0]))));
    const nations = Array.from(new Set(yearsAndNationsData.map(item => item[1])));
    //sort
    const yearsList = years.sort((a, b) => a - b);
    const nationsList = nations.sort((a, b) => a.localeCompare(b));

    return (
        <header className="header">
            {user ? (
                <DropdownButton id="dropdown-basic-button" title={`Xin chào ${user.split('@')[0]}`} data-bs-theme="dark">
                    <Dropdown.Item className="dropdown-item" href='/profile'>Trang cá nhân</Dropdown.Item>
                    <Dropdown.Item className="dropdown-item" href="#">Lịch sử xem phim</Dropdown.Item>
                    <Dropdown.Item className="dropdown-item" onClick={handleLogout}>Đăng xuất</Dropdown.Item>
                </DropdownButton>
            ) : (
                <button className='sign-in-button' onClick={handleClick}>Đăng nhập</button>
            )}
            <div className="title">
                <h5>Movie</h5>
                <img src={logo} alt="logo"/>

                <ol>
                    <li><Link to="/">MOVIE</Link></li>
                    <li className='category'>
                        THỂ LOẠI
                        <div className="category-list">
                            {genresData && genresData.map(genre => (
                                <Link key={genre.id} to={`/genres/${genre.id}/${genre.genreName}`}>{genre.genreName}</Link>
                            ))}
                        </div>
                    </li>
                    <li className='category'>
                        QUỐC GIA
                        <div className="category-list">
                            {nationsList && nationsList.map((nation, index) => (
                                <Link key={index} to={`/nations/${nation}`}>{nation}</Link>
                            ))}
                        </div>
                    </li>
                    <li className='category'>
                        NĂM PHÁT HÀNH
                        <div className="category-list">
                            {yearsList && yearsList.map((year, index) => (
                                <Link key={index} to={`/years/${year}`}>{year}</Link>
                            ))}
                        </div>
                    </li>
                    <li><Link to='movies/trailer'>TRAILER</Link></li>
                    <li><Link>TOP PHIM</Link></li>
                </ol>
            </div>

            <form onSubmit={handleSubmit}>
                <input type='text' className='search' placeholder='Tìm tên phim' 
                value={searchName} onChange={(event) => setSearchName(event.target.value)}/>
                <button className='searchBtn' type='submit'>🔍</button>
            </form>
        </header>
    );
}

export default Header