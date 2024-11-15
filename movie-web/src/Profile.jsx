import React, {useState, useEffect} from 'react';
import {
  MDBCol,
  MDBContainer,
  MDBRow,
  MDBCard,
  MDBCardText,
  MDBCardBody,
  MDBCardImage,
  MDBInput
} from 'mdb-react-ui-kit';
import axios from 'axios';
import Rating from '@mui/material/Rating';
import { Link } from 'react-router-dom';

export default function Profile({user}) {

  const [imgUrl, setImgUrl] = useState(null);
  const [profileId, setProfileId] = useState(null);
  const [ratedMovies, setRatedMovies] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/v1/user/${user}`);
        const response2 = await axios.get(`http://localhost:8080/api/v1/user/rating/${user}`);

        setFormData({
          name: response.data.accountProfile.fullName,
          email: response.data.email,
          phone: response.data.accountProfile.phoneNumber,
          address: response.data.accountProfile.address,
          gender: response.data.accountProfile.gender
        });
        setImgUrl(response.data.accountProfile.avatar);
        setProfileId(response.data.accountProfile.id)

        setRatedMovies(response2.data);
      }
      catch(error) {
      }
    };

    fetchData();

  }, [user]);

  const genderMap = {
    0: 'Nam',
    1: 'Nữ',
    2: 'Khác'
  };
  const [isEditing, setIsEditing] = useState(false);
  const [formData, setFormData] = useState({
    name: 'Johnatan Smith',
    email: 'example@example.com',
    phone: '(097) 234-5678',
    address: 'Bay Area, San Francisco, CA',
    gender: '0',
  });

  const toggleEdit = () => {
    setIsEditing(!isEditing);
  }

  const handleGenderChange = (e) => {
    setFormData({
      ...formData,
      gender: e.target.value
    });
  };

  const handleInputChange = (e) => {
    const {name, value} = e.target;
    setFormData({
      ...formData,
      [name]: value
    });
  };

  const saveChanges = async () => {
    try {
      await axios.put(`http://localhost:8080/api/v1/user/profile?id=${profileId}`, {
        "fullName": formData.name,
        "gender": formData.gender,
        "address": formData.address,
        "phoneNumber": formData.phone
      });
    }
    catch(error) {
    }

    toggleEdit();
  }

  return (
    <section style={{ backgroundColor: '#111111f7' }}>
      <MDBContainer className="py-5">
        <MDBRow>
          <MDBCol lg="4">
            <MDBCard className="p-3 mb-2 bg-dark bg-gradient text-white">
              <MDBCardBody className="text-center">
                <MDBCardImage
                  src={imgUrl}
                  alt="avatar"
                  className="rounded-circle"
                  style={{ width: '180px' }}
                  fluid />
                <p className="text-light mb-2">Xin chào {formData.name}</p>
                <div className="d-flex justify-content-center mb-2">
                  <button>Tải ảnh lên</button>
                </div>
              </MDBCardBody>
            </MDBCard>
          </MDBCol>
          <MDBCol lg="8">
            <MDBCard className="p-3 mb-2 bg-dark bg-gradient text-white">
              <MDBCardBody>
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText className="text-white">Email</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    {isEditing ?
                    (
                      <MDBInput
                      type="email"
                      name="email"
                      value={formData.email}
                      className="text-muted"
                      readOnly={true}
                      />
                    ) :
                    (
                      <MDBCardText className="text-white">{formData.email}</MDBCardText>
                    )}
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText className="text-white">Họ Tên</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    {isEditing ?
                    (
                      <MDBInput
                      type="text"
                      name="name"
                      value={formData.name}
                      onChange={handleInputChange}
                      className="text-muted"
                      />
                    ) :
                    (
                      <MDBCardText className="text-white">{formData.name}</MDBCardText>
                    )}
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText className="text-white">Số điện thoại</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    {isEditing ?
                    (
                      <MDBInput
                      type="tel"
                      name="phone"
                      value={formData.phone}
                      onChange={handleInputChange}
                      className="text-muted"
                      />
                    ) :
                    (
                      <MDBCardText className="text-white">{formData.phone}</MDBCardText>
                    )}
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText className="text-white">Địa chỉ</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                    {isEditing ?
                    (
                      <MDBInput
                      type="text"
                      name="address"
                      value={formData.address}
                      onChange={handleInputChange}
                      className="text-muted"
                      />
                    ) :
                    (
                      <MDBCardText className="text-white">{formData.address}</MDBCardText>
                    )}
                  </MDBCol>
                </MDBRow>
                <hr />
                <MDBRow>
                  <MDBCol sm="3">
                    <MDBCardText className="text-white">Giới tính</MDBCardText>
                  </MDBCol>
                  <MDBCol sm="9">
                  {isEditing ?
                    (
                      <select
                      className="gender-form-select text-muted"
                      value={formData.gender}
                      onChange={handleGenderChange}
                      >
                        <option value={0}>Nam</option>
                        <option value={1}>Nữ</option>
                        <option value={2}>Khác</option>
                      </select>                     
                    ) :
                    (
                      <MDBCardText className="text-white">{genderMap[formData.gender]}</MDBCardText>
                    )}
                  </MDBCol>
                </MDBRow>
              </MDBCardBody>
            </MDBCard>
            <div className='profile-Btn'>
              <button onClick={toggleEdit}>{isEditing ? 'Hủy' : 'Chỉnh sửa'}</button>
              {isEditing && <button onClick={saveChanges}>Lưu</button>}
            </div>
            <MDBRow>
              <MDBCol md="12">
                <MDBCard className="p-3 mb-2 bg-dark bg-gradient text-white">
                  <MDBCardBody>
                    <MDBCardText className="mb-4">Lịch sử đánh giá phim</MDBCardText>
                    <hr />
                    <MDBRow>
                      <MDBCol sm="6"><h5>Tên phim</h5></MDBCol>
                      <MDBCol><h5>Đánh giá</h5></MDBCol>
                      <MDBCol><h5>Ngày đánh giá</h5></MDBCol>
                    </MDBRow>
                    {ratedMovies && ratedMovies.map((item) => (
                      <>
                        <MDBRow>
                          <MDBCol sm="6">
                            <Link to={`/detail?id=${item.movie.id}`} key={item.movie.id}>
                              <MDBCardText style={{ fontSize: '.88rem' }}>{item.movie.title}</MDBCardText>
                            </Link>
                          </MDBCol>
                          <MDBCol>
                            <Rating
                              name="read-only"
                              value={item.stars}   
                              readOnly
                              size='small'
                            />
                          </MDBCol>
                          <MDBCol>
                          <MDBCardText style={{ fontSize: '.88rem' }}>{item.ratingDate}</MDBCardText>
                          </MDBCol>
                        </MDBRow>
                      </>
                    ))}
                  </MDBCardBody>
                </MDBCard>
              </MDBCol>
            </MDBRow>
          </MDBCol>
        </MDBRow>
      </MDBContainer>
    </section>
  );
}