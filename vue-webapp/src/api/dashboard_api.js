import axios from 'axios'

var root = 'http:/localhost:9999'

function dealResponse (res, success, error) {
  console.info('axios get response', res)
  if (res.data.success === true) {
    if (success) {
      success(res.data)
    }
  } else {
    if (error) {
      error(res.data)
    } else {
      window.alert('error: ' + JSON.stringify(res.data))
    }
  }
}

function dealError (res, error) {
  console.error('axios get error', res)
  if (res.response) {
    window.alert('api error, HTTP CODE: ' + res.response.status)
  }
}

export default {
  get: function (url, params, success, error) {
    axios.get(root + url, params)
      .then(res => dealResponse(res, success, error))
      .catch(res => dealError)
  }
}
