// <div id="container"></div>
import MapLoader from "@/assets/js/AMap.js";
export default {
  name: "HelloWorld",
  data() {
    return {
      msg: "Welcome to Your Vue.js App",
      map: null,
    };
  },
  methods: {
    getLocation() {
      const that = this;
      console.log(that);
      AMap.plugin("AMap.Geolocation", function () {
        var geolocation = new AMap.Geolocation({
          enableHighAccuracy: true, // 是否使用高精度定位，默认：true
          timeout: 10000, // 设置定位超时时间，默认：无穷大
          buttonOffset: new AMap.Pixel(10, 20), // 定位按钮的停靠位置的偏移量，默认：Pixel(10, 20)
          zoomToAccuracy: true, //  定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
          buttonPosition: "RB", //  定位按钮的排放位置,  RB表示右下
        });

        geolocation.getCurrentPosition();
        AMap.event.addListener(geolocation, "complete", onComplete);
        AMap.event.addListener(geolocation, "error", onError);

        function onComplete(data) {
          // data是具体的定位信息
          console.log(data);
          that.getLngLatLocation(data.position.lng, data.position.lat);
          that.map = new AMap.Map("container", {
            center: [data.position.lng, data.position.lat],
            zoom: 11,
          });
        }

        function onError(data) {
          // 定位出错
          console.log(data);
        }
      });
    },
    getLngLatLocation(a, b) {
      console.log(a + "," + b);

      this.$axios({
        url: "https://restapi.amap.com/v3/geocode/regeo",
        method: "get",
        params: {
          key: "4892d14927fded3d8750671b2546eea2",
          location: a + "," + b,
        },
      }).then((res) => {
        console.log(res);
      });

      
    },
    firstMap() {
      let _this = this;
      MapLoader().then(
        (AMap) => {
          console.log("地图加载成功");
          this.getLocation();
        },
        (e) => {
          console.log("地图加载失败", e);
        }
      );
    },
  },
  mounted() {
    this.firstMap()
  },
};