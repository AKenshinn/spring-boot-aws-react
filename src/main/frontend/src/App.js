import React, {useState, useEffect, useCallback} from 'react';
import logo from './logo.svg';
import './App.css';
import axios from "axios"
import {useDropzone} from 'react-dropzone'

const UserProfile = () => {
  const [userProfiles, setUserProfiles] = useState([]);

  const fetchUserProfiles = () => {
    axios.get("http://localhost:8080/api/v1/user-profile").then(res => {
      console.log(res);

      setUserProfiles(res.data);

      

    })
  };

  useEffect(() => {
    fetchUserProfiles();
  }, [])

  return userProfiles.map((userProfile, index) => {
    return (
      <div key={index}>
        {
          userProfile.uuid ? (
            <img 
              src={`http://localhost:8080/api/v1/user-profile/${userProfile.uuid}/image/download`} 
            />
          ) : null
        }
        <br />
        <h1>{userProfile.username}</h1>
        <p>{userProfile.uuid}</p>
        <Dropzone {...userProfile} />
        <br />
      </div>
    )
  });
}

function Dropzone({ uuid }) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    console.log(file);

    const formData = new FormData();
    formData.append("file", file);

    axios.post(`http://localhost:8080/api/v1/user-profile/${uuid}/image/upload`, 
        formData,
        {
          headers: {
            "Content-Type": "multipart/form-data"
          }
        }
    ).then(() => {
      console.log("File upload successfully");
    }).catch(err => {
      console.log(err);
    });
  }, [])
  const {getRootProps, getInputProps, isDragActive} = useDropzone({onDrop})

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {
        isDragActive ?
          <p>Drop the image here ...</p> :
          <p>Drag 'n' drop profile image, or click to select profile image</p>
      }
    </div>
  )
}

function App() {
  return (
    <div className="App">
      <UserProfile />
    </div>
  );
}

export default App;
