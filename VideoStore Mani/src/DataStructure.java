
public abstract class DataStructure<T extends Comparable<T>>  {
    
    public abstract void insert(T e);
    
    
    public abstract T remove(T e);
    
    public abstract T search(T e);

    @Override
    public abstract String toString();
    
    
}
