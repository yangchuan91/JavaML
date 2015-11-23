package ml.knn;
/**
 * @author yangchuan
 *
 */
public class Point {
	
	private double x;//x坐标
	private double y;//y坐标
	private int clusterId;//类别ID
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
