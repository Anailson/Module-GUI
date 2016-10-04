package interfaces;

import java.util.HashMap;

public interface GenericFormListener {

	public void save(HashMap<String, Object> values);

	public void clear();

	public void cancel();
	
	public void isRequired();
}
