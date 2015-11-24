package ml.knn;
/**
 * @author yangchuan
 * KNN�㷨��ģʵ����
 */
public class Point implements Comparable<Point> {
	
	private double x;//x����
	private double y;//y����
	private int clusterId;//���ID
	private double distance;//�����������������ݵľ���
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
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	@Override
	public String toString() {
		return "Point [x=" + x + ", y=" + y + ", clusterId=" + clusterId + ", distance=" + distance
				+ "]";
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
	@Override
	public int compareTo(Point o) {
		// TODO Auto-generated method stub
		return Double.valueOf(this.getDistance()).compareTo(Double.valueOf(o.getDistance()));
	}
}
