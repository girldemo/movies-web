import axios from "axios";
import React, {useState, useEffect, useContext, createContext} from "react";
import RecomendedMovie from "./RecomendedMovie";
import UpdatedMovie from "./UpdatedMovie";
import NewMovie from "./NewMovie";
import TrailerMovie from "./TrailerMovie";

function Body({user}) {

  return (
    <>
      <RecomendedMovie user={user}/>
      <UpdatedMovie/>
      <NewMovie/>
      <TrailerMovie/>
    </>
  )
}

export default Body;
