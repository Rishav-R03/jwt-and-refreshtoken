import http from 'k6/http';
import {check} from 'k6';

export let options = {
    vus: 100,
    duration: '30s',
};

export default function(){
    let payLoad = JSON.stringify({
    username:'rishav',
    password:'password123',
    });

    let res = http.post(
        'http://localhost:5000/api/auth/login',
        payLoad,
        {headers: {'Content-Type':'application/json'}}
    );
    check(res,{
    'status is 200':(r) => r.status==200,});
}