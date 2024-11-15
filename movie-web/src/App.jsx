import Header from "./Header"
import Footer from "./Footer"
import Body from "./Body"
import DetailMovie from "./DetailMovie"
import WatchMovie from "./WatchMovie"
import HeaderMovieList from "./HeaderMovieList"
import LoginRegister from "./LoginRegister"
import MovieList from "./MovieList"
import { BrowserRouter as Router, Route, Routes, Navigate, useLocation, json } from "react-router-dom"
import SignUp from "./SignUp"
import Profile from "./Profile"
import { useEffect, useState } from "react";

function App() {

  const [showHeaderAndFooter, setShowHeaderAndFooter] = useState(true);
  const [user, setUser] = useState(null);

  useEffect(() => {
    const storeUser = localStorage.getItem('user');
    if (storeUser) {
      setUser(JSON.parse(storeUser));
    }
  }, []);

  useEffect(() => {
    if (user) {
      localStorage.setItem('user', JSON.stringify(user));
    }
    else {
      localStorage.removeItem('user');
    }
  }, [user]);
  return (
    <Router>
      <>
        {showHeaderAndFooter && <Header user={user} setUser={setUser}/>}  
        <Routes>
          <Route path="/" element={<Body user={user}/>}/>
          <Route path="/login" element={<LoginRegister setUser={setUser}/>}/>
          <Route path="/signup" element={<SignUp/>}/>
          <Route path="/genres/:genreId/:genreName" element={<HeaderMovieList/>}/>
          <Route path="/nations/:nationName" element={<HeaderMovieList/>}/>
          <Route path="/years/:year" element={<HeaderMovieList/>}/>
          <Route path="/movies/:keyword" element={<HeaderMovieList/>}/>
          <Route path="/movies/title/:movieName" element={<HeaderMovieList/>}/>
          <Route path="/movies/search/:searchKey" element={<MovieList/>}/>
          <Route path="/detail" element={<DetailMovie user={user} />}/>
          <Route path="/watch" element={<WatchMovie user={user}/>}/>
          <Route path="/profile" element={user ? <Profile user={user}/> : <LoginRegister setUser={setUser}/>}/>
          <Route path="*" element={<Navigate to="/"/>}/>
        </Routes>
        {showHeaderAndFooter && <Footer/>}
      </>
    </Router>
  );
}

export default App
