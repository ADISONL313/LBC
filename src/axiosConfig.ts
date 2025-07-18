import axios from "axios";

// 如果你要用到session验证码功能，让请求携带cookie
// axios.defaults.withCredentials = true;

// 创建实例
const instance = axios.create({
  baseURL: 'http://localhost:8150/bysj',
  timeout: 15000,
});


// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 获取本地 seet 值
    const token = sessionStorage.getItem("token");
    if (token) {
      config.headers['Authorization'] = token;
    }
    if(config.url==='/news/upload'){
      config.headers['Content-Type'] = 'multipart/form-data';
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

//http response 拦截器
instance.interceptors.response.use(
  (response) => {
    // 拿到接口返回值，做逻辑处理
     return response.data
  	// if (response.data.code === 700||response.data.code === 200) {
    //   return response.data;
    // } else {
    //   // 抛出错误
    //   return Promise.reject({
    //     message: response.data.msg,
    //     type: "error",
    //   });
    // }
  }, (error) => {
    return Promise.reject(error)
  }
);

/**
 * 封装get方法
 * @param url
 * @param data
 * @returns {Promise}
 */
export const get = (
  url: string,
  paramenter?: object,
  other?: object
): Promise<any> => {
  return new Promise((resolve, reject) => {
    instance
      .get(url, { params: paramenter, ...other })
      .then((response) => {
        resolve(response);
      })
      .catch((err) => {
        reject(err);
      });
  });
};


/**
 * 封装delete方法
 * @param url
 * @returns {Promise}
 */
export const deleteRemove = (url: string): Promise<any> => {
  return new Promise((resolve, reject) => {
    instance
      .delete(url)
      .then((response) => {
        resolve(response);
      })
      .catch((err) => {
        reject(err);
      });
  });
};

/**
 * 封装put方法
 * @param url
 * @param data
 * @returns {Promise}
 */
export const put = (url: string, data?: object): Promise<any> => {
  return new Promise((resolve, reject) => {
    instance.put(url, data).then(
      (response) => {
        resolve(response);
      },
      (err) => {
        reject(err);
      }
    );
  });
};

/**
 * 封装post请求
 * @param url
 * @param data
 * @returns {Promise}
 */
export const post = (url: string, data?: object, other?: object): Promise<any> => {
  return new Promise((resolve, reject) => {
    instance.post(url, data, other).then(
      (response) => {
        resolve(response);
      },
      (err) => {
        reject(err);
      }
    );
  });
};

// 取消请求
export const controller = new AbortController();
