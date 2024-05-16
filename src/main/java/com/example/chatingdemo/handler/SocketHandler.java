//package com.example.chatingdemo.handler;
//
//import java.util.HashMap;
//import java.util.Iterator;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Component
//@Slf4j
//public class SocketHandler extends TextWebSocketHandler {
//
//    // WebSocket session을 담아둘 Map
//    HashMap<String, WebSocketSession> sessionMap = new HashMap<>();
//
//    // WebSocket session ID와 Member를 담아둘 Map
//    HashMap<String, String> sessionUserMap = new HashMap<>();
//
//    // WebSocket session ID와 Member를 담아둘 JSON_Object
//    JSONObject jsonUser = null;
//
//    // handleTextMessage Method는 message를 수신하면 동작
//    @Override
//    protected void handleTextMessage(WebSocketSession session,
//                                     TextMessage message) throws Exception {
//        log.info("SocketHandler handleTextMessage Start!!");
//        // Message 수신
//        String msg = message.getPayload();
//        log.info("SocketHandler handleTextMessage msg --> " + msg);
//
//        JSONObject jsonObject = jsonToObjectParser(msg);
//
//        // type를 get
//        String msgType = (String) jsonObject.get("type");
//        log.info("SocketHandler handleTextMessage msgType --> " + msgType);
//
//        switch (msgType) {
//            case "message":
//                jsonUser = new JSONObject(sessionUserMap);
//                log.info("message:jsonUser : " + jsonUser);
//
//                // 전송 대상을 기분으로 분기
//                String yourName = (String) jsonObject.get("yourName");
//                log.info("SocketHandler handleTextMessage yourName --> " + yourName);
//
//                // 전체
//                if (yourName.equals("ALL")) {
//                    for (String key : sessionMap.keySet()) {
//                        WebSocketSession wss = sessionMap.get(key);
//                        try {
//                            log.info("message key --> " + key);
//                            log.info("message wss --> " + wss);
//                            wss.sendMessage(new TextMessage(jsonObject.toJSONString()));
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }
//                } else { // 개인 전송 대상자(yourName은 개인 sessionId)
//                    // 상대방
//                    log.info("개인 전송 대상자 : 상대방 sessionId --> " + yourName);
//                    WebSocketSession wss1 = sessionMap.get(yourName);
//
//                    try {
//                        wss1.sendMessage(new TextMessage(jsonObject.toJSONString()));
//                        log.info("message wss1 --> " + wss1);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    // 나에게도 보내줘
//                    String meName = (String) jsonObject.get("sessionId");
//                    WebSocketSession wss2 = sessionMap.get(meName);
//                    log.info("개인 전송 대상자 : 나 --> " + meName);
//                    try {
//                        wss2.sendMessage(new TextMessage(jsonObject.toJSONString()));
//                        log.info("message wss2 --> " + wss2);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//
//            // sessionUserMap 등록
//            case "userSave":
//                // sessionUserMap에 sessionId와 userName 등록
//                String sessionId = (String) jsonObject.get("sessionId");
//                String userName = (String) jsonObject.get("userName");
//                String saveStatus = (String) jsonObject.get("saveStatus");
//
//                // 신규 등록
//                if (saveStatus.equals("Create")) {
//                    sessionUserMap.put(sessionId, userName);
//
//                    System.out.println("=================================================");
//                    System.out.println("== sessionUserMap 저장내용 조회하여 arrayJsonUser에   ==");
//                    System.out.println("==  각각의 JSONObject jsonUser로  변환                           ==");
//                    System.out.println("== 1. type : userSave                          ==");
//                    System.out.println("== 2. sessionId : sessionUserMap.sessionId     ==");
//                    System.out.println("== 3. userName  : sessionUserMap.userName      ==");
//                    System.out.println("=================================================");
//
//                } else { // Delete
//                    log.info("handleTextMessage userSave:userDelete Start!");
//                    log.info("handleTextMessage userSave:userDelete session.getId --> " + session.getId());
//
//                    // WebSocket 종료
//                    sessionMap.remove(session.getId());
//                    // sessionUserMap 종료
//                    sessionUserMap.remove(session.getId());
//                    // break;
//                }
//
//                JSONArray arraryJsonUser = new JSONArray();
//                log.info("1. type : userSave");
//
//                Iterator<String> mapIter = sessionUserMap.keySet().iterator();
//                log.info("2. sessionId : sessionUserMap.sessionId");
//                log.info("3. userName : sessionUserMap.userName");
//
//                while (mapIter.hasNext()) {
//                    String key = mapIter.next();
//                    String value = sessionUserMap.get(key);
//                    log.info("key :  value --> " + key + " : " + value);
//
//                    jsonUser = new JSONObject();
//
//                    jsonUser.put("type", "userSave");
//                    jsonUser.put("sessionId", key);
//                    jsonUser.put("userName", value);
//
//                    arraryJsonUser.add(jsonUser);
//
//                }
//                log.info("session을 GET하여 User 내용 전송");
//                log.info("arraryJsonUser : " + arraryJsonUser);
//
//                // 전체에 User 등록을 하게 함
//                for (String key : sessionMap.keySet()) {
//                    WebSocketSession wss = sessionMap.get(key);
//
//                    try {
//                        wss.sendMessage(new TextMessage(arraryJsonUser.toJSONString()));
//                        log.info("userSave wss --> " + wss);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//                break;
//
//            case "userDelete":
//                log.info("handleTextMessage case:userDelete Start!");
//                log.info("handleTextMessage case:userDelete session.getId --> " + session.getId());
//
//                // WebSocket 종료
//                sessionMap.remove(session.getId());
//
//                // sessionUserMap 종료
//                sessionUserMap.remove(session.getId());
//
//                break;
//        }
//
//    }
//
//    // @SuppressWarning
//    // 이건 컴파일러가 일반적으로 경고하는 내용 중 "이건 하지마" 하고 제외시킬 때 쓰임
//    // 따라서 어떤 경고를 제외시킬지 옵션
//    // 1. all : 모든 경고를 억제
//    // 2. cast : 캐스트 연산자 관련 경고 억제
//    // 3. dep-ann : 사용하지 말아야 할 주석 관련 경고 억제
//    // 4. deprecation : 사용하지 말아야 할 메소드 관련 경고 억제
//    // 5. fallthrough : switch문에서의 break 누락 관련 경고 억제
//    // 6. finally : 반환하지 않는 finally 블럭 관련 경고 억제
//    // 7. null : null 분석 관련 경고 억제
//    // 8. rawtypes : 제네릭을 사용하는 클래스 매개 변수가 불특정일 때의 경고 억제
//    // 9. unchecked : 검증되지 않은 연산자 관련 경고 억제
//    // 10. unused : 사용하지 않는 코드 관련 경고 억제
//
//    @SuppressWarnings("unchecked")
//    // WebSocket이 연결 되면 동작
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        log.info("SocketHandler afterConnectionEstablished Start!!");
//
//        // WebSocket이 연결 되면 동작
//        super.afterConnectionEstablished(session);
//
//        // 연결 소켓을 MAP에 등록
//        sessionMap.put(session.getId(), session);
//        log.info("SocketHandler afterConnectionEstablished session.getId --> " + session.getId());
//        log.info("SocketHandler afterConnectionEstablished session --> " + session);
//        JSONObject jsonObject = new JSONObject();
//
//        // 대상 : client
//        jsonObject.put("type", "getId");
//        jsonObject.put("sessionId", session.getId());
//
//        // session server 등록 -> socket server가 client에게 전송
//        session.sendMessage(new TextMessage(jsonObject.toJSONString()));
//
//    }
//
//    // WebSocket이 종료 되면 동작
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
//        log.info("SocketHandler afterConnectionClosed Start!!");
//        log.info("SocketHandler afterConnectionClosed session.getId --> " + session.getId());
//        // WebSocket end
//        sessionMap.remove(session.getId());
//
//        super.afterConnectionClosed(session, status);
//    }
//
//    // handleTextMessage Method에 JSON file이 들어오면 Parsing 해주는 함수를 추가
//    private static JSONObject jsonToObjectParser(String jsonStr) {
//        log.info("SocketHandler jsonToObjectParser Start!!");
//
//        JSONParser parser = new JSONParser();
//        JSONObject jsonObject = null;
//
//        log.info("SocketHandler jsonToObjectParser jsonStr --> " + jsonStr);
//
//        try {
//            jsonObject = (JSONObject) parser.parse(jsonStr);
//            log.info("SocketHandler jsonToObjectParser jsonObject --> " + jsonObject);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        return jsonObject;
//    }
//
//}
