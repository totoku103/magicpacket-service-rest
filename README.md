<h1>README</h1>
WOL 기능을 사용할 때마다 로그인 후 기능 활성화하는 행위를 줄이기 위해 REST로 구현.

<h3>WOL이란?</h3>
AMD와 Hewlett Packard가 원격으로 호스트를 깨우기 위해 개발한 Magic Packet 기술이 부여된 프로토콜 이름.

<h3>Magic Packet White Paper(AMD)</h3>
https://www.amd.com/system/files/TechDocs/20213.pdf


<h3>구현 내용</h3>
- udp Port(9)를 사용한다.
- magic packet을 날려야 할 대상의 mac 주소와 boradcast ip를 알고 있어야 한다.
  - 대상 값은 application-{profile}.yaml에 정의.
  - _**실제 사용할 용도는 git에 commit 하지 않았음.**_
- magic packet의 형식은 다음과 같다.

    |Synchronization Stream | Target MAC | Password (optional)|
    |:---:|:---:|:---:|
    |6|96|0,4 or 6|

https://wiki.wireshark.org/WakeOnLAN

- Synchronization Stream은 0xff로 정의한다(6byte).
- Target MAC은 구분자 제외 16진수로 변환한다.
- Password는 사용하지 않으니 패킷 정의하지 않는다.


