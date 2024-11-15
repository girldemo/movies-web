import axios from "axios";

const API_URL = "http://localhost:8080/api/v1";

export const register = (email, password) => {
    return axios.post(`${API_URL}/user/register`, {email, password});
};

export const login = (email, password) => {
    return axios.post(`${API_URL}/user/login`, {email, password});
};
