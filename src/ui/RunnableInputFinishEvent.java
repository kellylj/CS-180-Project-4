package ui;

import java.util.Map;

public interface RunnableInputFinishEvent {
	public MenuState onInputFinish(Map<String, String> results);
}