# 웹 서버, 웹 애플리케이션 서버

Web Server와 Web Application Server는 어떤 관점에서 보느냐에 따라 달라질 수 있다<br/>
일반적으로 static resource (image, video 등) 제공 특화된 것이 Web Server고<br/>
애플리케이션 로직을 실행해 DB에 접근하고 동적으로 데이터를 제공하는 것이 Web Application Server다<br/>

Web Application Code 수행은 정적 리소스 서빙에 비해 비용이 비싸다<br/>
WAS 하나로 서버 구성 시에는 리소스 서빙과 같은 중요치 않은 작업에 의해 중요한 비지니스 로직이 방해 받을 수 있다<br/>
따라서 Client -> Web Server -> WAS -> DB 와 같은 형태로 구성하며 동적 리소스가 필요한 작업을 Web -> WAS 위임해 동작한다<br/>

Web Server, WAS 분리해두면 트래픽이 많이 몰리는 곳을 독립적으로 scale out 때릴 수 있다<br/>
정적 리소스 요청이 많으면 Web Server 증설 혹은 CDN 사용<br/>
동적 리소스 요청이 많으면 WAS 증설 혹은 비동기 방식으로 변경

현대의 Web Server는 다양한 기능을 포함하는데 Load Balancing도 할 수 있고 CDN을 통해 캐싱된 리소스를 제공할 수도 있다<br/>
요즘의 백엔드는 HTTP, REST 기반의 통신을 주로 하기 때문에 웹 서버 없이 데이터만 제공하는 형태로 WAS로만 구성할 수 있다 