package ml.kmeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * 聚类算法的主体
 * @author yangchuan
 *
 */
public class Kmeans {
	int k; // 指定划分的簇数
    double mu; // 迭代终止条件，当各个新质心相对于老质心偏移量小于mu时终止迭代
    int repeat; // 重复运行次数
    
	public Kmeans(int k,int repeat,double mu) {
		super();
		this.k = k;
		this.repeat=repeat;
	}
	//随机初始化K个聚类中心
	public List<Point> initCenters(int k,List<Point> players){
		List<Point> lastCenters=new ArrayList<Point>(); 
		Set<Integer> randomSet=getRandomNumSet(players.size(), k);
		int count=0;
		for(Integer index:randomSet){
			Point center=players.get(index);
			center.setClusterId(count);
			count++;
			lastCenters.add(center);
		}
		System.out.println("初始化聚类中心："+lastCenters);
		return lastCenters;
	}
	//随机生成不重复的K个中心
	public static Set<Integer> getRandomNumSet(int range,int k){
		Set<Integer> randomSet=new HashSet<Integer>();
		while(randomSet.size()<k){
			randomSet.add((int)(Math.random()*range));
		}
		return randomSet;
	}
	//把数据集中的每个点归到离它最近的那个质心
	public List<Point> classify(List<Point> players,List<Point> lastCenters){
		System.out.println("旧的聚类中心为："+lastCenters);
		//循环计算每个点到质心的距离
		for(int i=0;i<players.size();i++){
			Point point=players.get(i);
			//记录距离最短的质心的索引值
			int index=0;
			//记录最小的距离
			double minDistance=calDistance(point, lastCenters.get(0));
			//循环遍历所有质心，计算出最小的距离和最近质心的索引值
			for(int j=1;j<lastCenters.size();j++){
				double dis=calDistance(point, lastCenters.get(j));
				if(minDistance>dis){
					index=j;
					minDistance=dis;
				}
			}
			//将点划分到最近的质心当中
			point.setClusterId(lastCenters.get(index).getClusterId());
		}
		System.out.println("分类后的数据分："+players);
		return players;
	}
	//计算两个点之间的距离
	public double calDistance(Point point,Point center){
		double dis=(point.getX()-center.getX())*(point.getX()-center.getX())+
				(point.getY()-center.getY())*(point.getY()-center.getY());
		return Math.sqrt(dis);
		
	}
	//计算新的聚类中心
	public List<Point> calNewCenters(List<Point> players,List<Point> oldcenters){
		List<Point> newCenters=new ArrayList<Point>(); 
		//总计有K个聚类中心,分别是1,2...,k-1,k
		for(int i=0;i<k;i++){
			//找出每个聚类的元素并且计算新的聚类中心
			List<Point> clusters=new ArrayList<Point>();
			for(int j=0;j<players.size();j++){
				//如果聚类中心id等于i,则将其加入到聚类的List中进行计算
				if(players.get(j).getClusterId()==i){
					clusters.add(players.get(j));
				}
			}
			//对每个聚类的元素进行计算
			double sumX=0;//进球数总数
			double sumY=0;//抢断数总数
			for(Point player:clusters){
				sumX+=player.getX();
				sumY+=player.getY();
			}
			//计算平均的进球数和抢断数
			double aveX=sumX/clusters.size();
			double aveY=sumY/clusters.size();
			//初始化临时的聚类中心
			Point newcenter=new Point(aveX, aveY,i);
//			//记录新聚类中心的索引值
//			int index=0;
//			//记录每个点到临时聚类中心的距离
//			double minDistance=calDistance(tmpcenter, clusters.get(0));
//			//循环计算，然后获取新的聚类中心
//			for(int j=1;j<clusters.size();j++){
//				double dis=calDistance(tmpcenter, clusters.get(j));
//				if(minDistance>dis){
//					index=j;
//					minDistance=dis; 
//				}
//			}
			//将新的聚类中心加入到centers中
			newCenters.add(newcenter);
		}
		System.out.println("新的聚类中心是："+newCenters);
		return newCenters;
	}
	//计算新的聚类中心和前一次的聚类中心的距离偏移量，如果所有偏移量都小于阈值mu，则代表新的聚类中心已经满足要求，是合理解
	public boolean calCenterOffSet(double mu,List<Point> oldcenters,List<Point> newcenters){
		boolean b=true;
		for(int i=0;i<k;i++){
			double offset=calDistance(oldcenters.get(i),newcenters.get(i));
			System.out.print(offset+",");
			if(offset>mu){
				b=false;
				break;
			}
		}
		return b;
	}
	
	public static void main(String[] args) {
		//GenOriData.writeDataSet("E:/player.txt", 500, 50);
		//从文件中读取数据集
		List<Point> players=GenOriData.getOriPlaysData("E:/player.txt");
		int k=10; //聚类中心的个数
		double mu=0.05; //偏移量阈值
		int repeat=60;
		//初始化聚类算法类
		Kmeans kmeans=new Kmeans(k,repeat,mu);
		//随机初始化第一个聚类中心
		List<Point> oldcenters=kmeans.initCenters(k, players);
		List<Point> newcenters=null;
		//循环进行聚类运算
		for(int i=0;i<kmeans.repeat;i++){
			System.out.println("------------------第"+i+"次聚类开始--------------------");
			players=kmeans.classify(players,oldcenters);
			newcenters=kmeans.calNewCenters(players,oldcenters);
			//如果计算的的偏移量小于阈值，则终止循环
			if(kmeans.calCenterOffSet(kmeans.mu,oldcenters,newcenters)){
				System.out.println("第"+i+"次聚类的解是最终解："+newcenters);
				break;
			}
			//设置旧的聚类中心
			oldcenters.clear();
			oldcenters.addAll(newcenters);
			System.out.println("------------------第"+i+"次聚类结束--------------------");
		}
		
	}
	
	
	
    
    

}
