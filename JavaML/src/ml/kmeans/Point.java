package ml.kmeans;
/**
 * 需要聚类的实体类，有两个属性进球数和抢断数，根据这两个属性对球员进行分类
 * @author yangchuan
 *
 */
public class Point {
	
	private double x;//进球数
	private double y;//抢断书
	private int clusterId;//聚类ID，标识此球员属于哪个聚类
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getClusterId() {
		return clusterId;
	}
	public void setClusterId(int clusterId) {
		this.clusterId = clusterId;
	}
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", clusterId=" + clusterId + "]";
	}
	public Point(double x, double y, int clusterId) {
		super();
		this.x = x;
		this.y = y;
		this.clusterId = clusterId;
	}
	public Point(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	
	
	

}
