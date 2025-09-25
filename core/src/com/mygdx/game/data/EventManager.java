package com.mygdx.game.data;

//public class EventManager {
//    private static EventManager instance;
//    private Map<Class<? extends GameEvent>, List<EventListener>> listeners;
//
//    public <T extends GameEvent> void addListener(Class<T> eventType, EventListener<T> listener) {
//        listeners.computeIfAbsent(eventType, k -> new ArrayList<>()).add(listener);
//    }
//
//    public void fireEvent(GameEvent event) {
//        List<EventListener> eventListeners = listeners.get(event.getClass());
//        if (eventListeners != null) {
//            for (EventListener listener : eventListeners) {
//                listener.onEvent(event);
//            }
//        }
//    }
//}
