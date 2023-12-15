package database;

public interface DatabaseOperations {
	void create(Object obj);
    Object read(int id);
    void update(Object obj);
    void delete(int id);
}
