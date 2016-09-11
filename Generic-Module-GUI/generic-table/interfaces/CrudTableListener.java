package interfaces;

public interface CrudTableListener <T> {
	
	public void detail(int row);
	public void edit(int row, T obj);
	public void delete(int row, T obj);
}