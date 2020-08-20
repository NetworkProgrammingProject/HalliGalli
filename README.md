# HalliGalli
HalliGalli  Online

## 개요
4인용 온라인 할리갈리 게임.
대기 큐 지원으로 온라인 사용자 매칭이 가능

## 사용기술
- Swing
- RMI
- SSL

## 구조
`client` < - > `RMIinterface` < - > `Gamemanager` < - > `Game`
