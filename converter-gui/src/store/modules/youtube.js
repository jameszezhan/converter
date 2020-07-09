import axios from 'axios';

const state = {
    playlists:[
        // {
        //   "kind": "youtube#playlist",
        //   "etag": "hzFwj9aterEVgkLUz43zM7YGUX0",
        //   "id": "PLIIUN_0KHZtCxUQbzWccUkXrUi7aLYeOf",
        //   "snippet": {
        //     "publishedAt": "2019-05-23T20:18:32Z",
        //     "channelId": "UCMOHwfYab9sSCbNpXutx6Cw",
        //     "title": "K&J",
        //     "description": "",
        //     "thumbnails": {
        //       "default": {
        //         "url": "https://i.ytimg.com/vi/f1PWZ9hs1lc/default.jpg",
        //         "width": 120,
        //         "height": 90
        //       },
        //       "medium": {
        //         "url": "https://i.ytimg.com/vi/f1PWZ9hs1lc/mqdefault.jpg",
        //         "width": 320,
        //         "height": 180
        //       },
        //       "high": {
        //         "url": "https://i.ytimg.com/vi/f1PWZ9hs1lc/hqdefault.jpg",
        //         "width": 480,
        //         "height": 360
        //       },
        //       "standard": {
        //         "url": "https://i.ytimg.com/vi/f1PWZ9hs1lc/sddefault.jpg",
        //         "width": 640,
        //         "height": 480
        //       },
        //       "maxres": {
        //         "url": "https://i.ytimg.com/vi/f1PWZ9hs1lc/maxresdefault.jpg",
        //         "width": 1280,
        //         "height": 720
        //       }
        //     },
        //     "channelTitle": "James Chen",
        //     "localized": {
        //       "title": "K&J",
        //       "description": ""
        //     }
        //   }
        // },
        // {
        //   "kind": "youtube#playlist",
        //   "etag": "IW-VCSnxA3qHGEr5gNyLf3Pfllc",
        //   "id": "PLIIUN_0KHZtDtknC7Trlcxsig6Lpz3G9F",
        //   "snippet": {
        //     "publishedAt": "2018-09-27T06:25:20Z",
        //     "channelId": "UCMOHwfYab9sSCbNpXutx6Cw",
        //     "title": "Recipes",
        //     "description": "",
        //     "thumbnails": {
        //       "default": {
        //         "url": "https://i.ytimg.com/vi/Y4Xxcj4VeS8/default.jpg",
        //         "width": 120,
        //         "height": 90
        //       },
        //       "medium": {
        //         "url": "https://i.ytimg.com/vi/Y4Xxcj4VeS8/mqdefault.jpg",
        //         "width": 320,
        //         "height": 180
        //       },
        //       "high": {
        //         "url": "https://i.ytimg.com/vi/Y4Xxcj4VeS8/hqdefault.jpg",
        //         "width": 480,
        //         "height": 360
        //       },
        //       "standard": {
        //         "url": "https://i.ytimg.com/vi/Y4Xxcj4VeS8/sddefault.jpg",
        //         "width": 640,
        //         "height": 480
        //       },
        //       "maxres": {
        //         "url": "https://i.ytimg.com/vi/Y4Xxcj4VeS8/maxresdefault.jpg",
        //         "width": 1280,
        //         "height": 720
        //       }
        //     },
        //     "channelTitle": "James Chen",
        //     "localized": {
        //       "title": "Recipes",
        //       "description": ""
        //     }
        //   }
        // },
        // {
        //   "kind": "youtube#playlist",
        //   "etag": "frrJG_RwLYmVlGqOdGO78G9ZpBI",
        //   "id": "PLIIUN_0KHZtBYFl4hUMbawLICqfuXg09l",
        //   "snippet": {
        //     "publishedAt": "2018-07-07T20:13:31Z",
        //     "channelId": "UCMOHwfYab9sSCbNpXutx6Cw",
        //     "title": "Cleaning",
        //     "description": "",
        //     "thumbnails": {
        //       "default": {
        //         "url": "https://i.ytimg.com/vi/9swNxTSU7Gg/default.jpg",
        //         "width": 120,
        //         "height": 90
        //       },
        //       "medium": {
        //         "url": "https://i.ytimg.com/vi/9swNxTSU7Gg/mqdefault.jpg",
        //         "width": 320,
        //         "height": 180
        //       },
        //       "high": {
        //         "url": "https://i.ytimg.com/vi/9swNxTSU7Gg/hqdefault.jpg",
        //         "width": 480,
        //         "height": 360
        //       },
        //       "standard": {
        //         "url": "https://i.ytimg.com/vi/9swNxTSU7Gg/sddefault.jpg",
        //         "width": 640,
        //         "height": 480
        //       },
        //       "maxres": {
        //         "url": "https://i.ytimg.com/vi/9swNxTSU7Gg/maxresdefault.jpg",
        //         "width": 1280,
        //         "height": 720
        //       }
        //     },
        //     "channelTitle": "James Chen",
        //     "localized": {
        //       "title": "Cleaning",
        //       "description": ""
        //     }
        //   }
        // },
        // {
        //   "kind": "youtube#playlist",
        //   "etag": "32Lqmk3sHXk_BUNA4u5P0DhNSmk",
        //   "id": "PLIIUN_0KHZtCEcu_sSsATezQpefP0_UJe",
        //   "snippet": {
        //     "publishedAt": "2018-06-21T05:00:31Z",
        //     "channelId": "UCMOHwfYab9sSCbNpXutx6Cw",
        //     "title": "Everyday",
        //     "description": "",
        //     "thumbnails": {
        //       "default": {
        //         "url": "https://i.ytimg.com/vi/b_rIk8yKDGI/default.jpg",
        //         "width": 120,
        //         "height": 90
        //       },
        //       "medium": {
        //         "url": "https://i.ytimg.com/vi/b_rIk8yKDGI/mqdefault.jpg",
        //         "width": 320,
        //         "height": 180
        //       },
        //       "high": {
        //         "url": "https://i.ytimg.com/vi/b_rIk8yKDGI/hqdefault.jpg",
        //         "width": 480,
        //         "height": 360
        //       },
        //       "standard": {
        //         "url": "https://i.ytimg.com/vi/b_rIk8yKDGI/sddefault.jpg",
        //         "width": 640,
        //         "height": 480
        //       },
        //       "maxres": {
        //         "url": "https://i.ytimg.com/vi/b_rIk8yKDGI/maxresdefault.jpg",
        //         "width": 1280,
        //         "height": 720
        //       }
        //     },
        //     "channelTitle": "James Chen",
        //     "localized": {
        //       "title": "Everyday",
        //       "description": ""
        //     }
        //   }
        // },
        // {
        //   "kind": "youtube#playlist",
        //   "etag": "yyDE5AYS7kMWUDw6W3jq433siPE",
        //   "id": "PLIIUN_0KHZtA5QfIcpqk5_gcGW9ge_MQH",
        //   "snippet": {
        //     "publishedAt": "2018-06-18T02:02:37Z",
        //     "channelId": "UCMOHwfYab9sSCbNpXutx6Cw",
        //     "title": "Hacks",
        //     "description": "",
        //     "thumbnails": {
        //       "default": {
        //         "url": "https://i.ytimg.com/vi/umsK-9MBqOs/default.jpg",
        //         "width": 120,
        //         "height": 90
        //       },
        //       "medium": {
        //         "url": "https://i.ytimg.com/vi/umsK-9MBqOs/mqdefault.jpg",
        //         "width": 320,
        //         "height": 180
        //       },
        //       "high": {
        //         "url": "https://i.ytimg.com/vi/umsK-9MBqOs/hqdefault.jpg",
        //         "width": 480,
        //         "height": 360
        //       },
        //       "standard": {
        //         "url": "https://i.ytimg.com/vi/umsK-9MBqOs/sddefault.jpg",
        //         "width": 640,
        //         "height": 480
        //       },
        //       "maxres": {
        //         "url": "https://i.ytimg.com/vi/umsK-9MBqOs/maxresdefault.jpg",
        //         "width": 1280,
        //         "height": 720
        //       }
        //     },
        //     "channelTitle": "James Chen",
        //     "localized": {
        //       "title": "Hacks",
        //       "description": ""
        //     }
        //   }
        // },
        // {
        //   "kind": "youtube#playlist",
        //   "etag": "xOZ9_pfm1_206vm4XPSoBvmRCbU",
        //   "id": "PLIIUN_0KHZtBN1bbCWKdC9CNrxbqWOIkH",
        //   "snippet": {
        //     "publishedAt": "2018-03-07T14:09:48Z",
        //     "channelId": "UCMOHwfYab9sSCbNpXutx6Cw",
        //     "title": "Gym",
        //     "description": "",
        //     "thumbnails": {
        //       "default": {
        //         "url": "https://i.ytimg.com/vi/2TzewWk-fdk/default.jpg",
        //         "width": 120,
        //         "height": 90
        //       },
        //       "medium": {
        //         "url": "https://i.ytimg.com/vi/2TzewWk-fdk/mqdefault.jpg",
        //         "width": 320,
        //         "height": 180
        //       },
        //       "high": {
        //         "url": "https://i.ytimg.com/vi/2TzewWk-fdk/hqdefault.jpg",
        //         "width": 480,
        //         "height": 360
        //       },
        //       "standard": {
        //         "url": "https://i.ytimg.com/vi/2TzewWk-fdk/sddefault.jpg",
        //         "width": 640,
        //         "height": 480
        //       },
        //       "maxres": {
        //         "url": "https://i.ytimg.com/vi/2TzewWk-fdk/maxresdefault.jpg",
        //         "width": 1280,
        //         "height": 720
        //       }
        //     },
        //     "channelTitle": "James Chen",
        //     "localized": {
        //       "title": "Gym",
        //       "description": ""
        //     }
        //   }
        // },
        // {
        //   "kind": "youtube#playlist",
        //   "etag": "ZcDcLX17nlx0bSuVTmVS2ZZP4NE",
        //   "id": "PLIIUN_0KHZtANRIUnlBzM0006kRtftKQQ",
        //   "snippet": {
        //     "publishedAt": "2017-10-18T02:29:13Z",
        //     "channelId": "UCMOHwfYab9sSCbNpXutx6Cw",
        //     "title": "python",
        //     "description": "",
        //     "thumbnails": {
        //       "default": {
        //         "url": "https://i.ytimg.com/vi/FsAPt_9Bf3U/default.jpg",
        //         "width": 120,
        //         "height": 90
        //       },
        //       "medium": {
        //         "url": "https://i.ytimg.com/vi/FsAPt_9Bf3U/mqdefault.jpg",
        //         "width": 320,
        //         "height": 180
        //       },
        //       "high": {
        //         "url": "https://i.ytimg.com/vi/FsAPt_9Bf3U/hqdefault.jpg",
        //         "width": 480,
        //         "height": 360
        //       },
        //       "standard": {
        //         "url": "https://i.ytimg.com/vi/FsAPt_9Bf3U/sddefault.jpg",
        //         "width": 640,
        //         "height": 480
        //       },
        //       "maxres": {
        //         "url": "https://i.ytimg.com/vi/FsAPt_9Bf3U/maxresdefault.jpg",
        //         "width": 1280,
        //         "height": 720
        //       }
        //     },
        //     "channelTitle": "James Chen",
        //     "localized": {
        //       "title": "python",
        //       "description": ""
        //     }
        //   }
        // }
    ],
    tracks: []
};

const getters = {
    allPlaylists: (state) => state.playlists,
    allYtTracks: (state) => state.tracks
};

const actions = {
    async fetchAllPlaylists( {state, commit, rootState} ){
        console.log(state);
        const response = await axios.get(
            'http://localhost:8080/api/v1/youtube/playlist?state=' + rootState.uuids.uuids.youtube,
            {
                headers:{
                    Accept: "application/json"
                }
            }
        );
        console.log(response);
        commit("setPlaylists", response.data.items)
    },

    async getTracksFromPlaylists( {state, commit, rootState} ){
        console.log(state);
        var ids = [];
        state.playlists.map(
            playlist => {
                if(playlist.checked){
                    ids.push(playlist.id)
                }
            }
        )
        console.log(ids);
        var data = JSON.stringify(ids);
        const response = await axios({
          method:"post",
          url: "http://localhost:8080/api/v1/youtube/tracks?state=" + rootState.uuids.uuids.youtube,
          headers: { 
            'Content-Type': 'application/json'
          },
          data: data
        });
        console.log(JSON.parse(response.data.PLIIUN_0KHZtCxUQbzWccUkXrUi7aLYeOf).items);
        commit("setTracks", response.data)
    },

    async toggleAll( {commit}, status){
        commit("toggleAll", status);
    }
};

const mutations = {
    setPlaylists: (state, playlists) => {
      state.playlists = playlists;
      state.playlists.map(
          playlist => playlist.checked = false
      )
      console.log(state.playlists);
    },
    setTracks: (state, tracks) => {
        for(const [key, value] of Object.entries(tracks)){
            console.log(key);
            console.log(JSON.parse(value).items);
            state.tracks = state.tracks.concat(JSON.parse(value).items);
        }
        state.tracks.map(
            tracks => tracks.checked = true
        )
    },
    toggleAll: (state, status) => {
        state.tracks = state.tracks.map(
            track => {
                track.checked = status
                return track;
            }
        )
        console.log(state.tracks);
    }
};

export default {
    state,
    getters,
    actions,
    mutations
}