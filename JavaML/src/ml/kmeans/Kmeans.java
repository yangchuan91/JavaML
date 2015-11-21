package ml.kmeans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * �����㷨������
 * @author yangchuan
 *
 */
public class Kmeans {
	int k; // ָ�����ֵĴ���
    double mu; // ������ֹ�����������������������������ƫ����С��muʱ��ֹ����
    int repeat; // �ظ����д���
    
	public Kmeans(int k,int repeat,double mu) {
		super();
		this.k = k;
		this.repeat=repeat;
	}
	//�����ʼ��K����������
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
		System.out.println("��ʼ���������ģ�"+lastCenters);
		return lastCenters;
	}
	//������ɲ��ظ���K������
	public static Set<Integer> getRandomNumSet(int range,int k){
		Set<Integer> randomSet=new HashSet<Integer>();
		while(randomSet.size()<k){
			randomSet.add((int)(Math.random()*range));
		}
		return randomSet;
	}
	//�����ݼ��е�ÿ����鵽����������Ǹ�����
	public List<Point> classify(List<Point> players,List<Point> lastCenters){
		System.out.println("�ɵľ�������Ϊ��"+lastCenters);
		//ѭ������ÿ���㵽���ĵľ���
		for(int i=0;i<players.size();i++){
			Point point=players.get(i);
			//��¼������̵����ĵ�����ֵ
			int index=0;
			//��¼��С�ľ���
			double minDistance=calDistance(point, lastCenters.get(0));
			//ѭ�������������ģ��������С�ľ����������ĵ�����ֵ
			for(int j=1;j<lastCenters.size();j++){
				double dis=calDistance(point, lastCenters.get(j));
				if(minDistance>dis){
					index=j;
					minDistance=dis;
				}
			}
			//���㻮�ֵ���������ĵ���
			point.setClusterId(lastCenters.get(index).getClusterId());
		}
		System.out.println("���������ݷ֣�"+players);
		return players;
	}
	//����������֮��ľ���
	public double calDistance(Point point,Point center){
		double dis=(point.getX()-center.getX())*(point.getX()-center.getX())+
				(point.getY()-center.getY())*(point.getY()-center.getY());
		return Math.sqrt(dis);
		
	}
	//�����µľ�������
	public List<Point> calNewCenters(List<Point> players,List<Point> oldcenters){
		List<Point> newCenters=new ArrayList<Point>(); 
		//�ܼ���K����������,�ֱ���1,2...,k-1,k
		for(int i=0;i<k;i++){
			//�ҳ�ÿ�������Ԫ�ز��Ҽ����µľ�������
			List<Point> clusters=new ArrayList<Point>();
			for(int j=0;j<players.size();j++){
				//�����������id����i,������뵽�����List�н��м���
				if(players.get(j).getClusterId()==i){
					clusters.add(players.get(j));
				}
			}
			//��ÿ�������Ԫ�ؽ��м���
			double sumX=0;//����������
			double sumY=0;//����������
			for(Point player:clusters){
				sumX+=player.getX();
				sumY+=player.getY();
			}
			//����ƽ���Ľ�������������
			double aveX=sumX/clusters.size();
			double aveY=sumY/clusters.size();
			//��ʼ����ʱ�ľ�������
			Point newcenter=new Point(aveX, aveY,i);
//			//��¼�¾������ĵ�����ֵ
//			int index=0;
//			//��¼ÿ���㵽��ʱ�������ĵľ���
//			double minDistance=calDistance(tmpcenter, clusters.get(0));
//			//ѭ�����㣬Ȼ���ȡ�µľ�������
//			for(int j=1;j<clusters.size();j++){
//				double dis=calDistance(tmpcenter, clusters.get(j));
//				if(minDistance>dis){
//					index=j;
//					minDistance=dis; 
//				}
//			}
			//���µľ������ļ��뵽centers��
			newCenters.add(newcenter);
		}
		System.out.println("�µľ��������ǣ�"+newCenters);
		return newCenters;
	}
	//�����µľ������ĺ�ǰһ�εľ������ĵľ���ƫ�������������ƫ������С����ֵmu��������µľ��������Ѿ�����Ҫ���Ǻ����
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
		//���ļ��ж�ȡ���ݼ�
		List<Point> players=GenOriData.getOriPlaysData("E:/player.txt");
		int k=10; //�������ĵĸ���
		double mu=0.05; //ƫ������ֵ
		int repeat=60;
		//��ʼ�������㷨��
		Kmeans kmeans=new Kmeans(k,repeat,mu);
		//�����ʼ����һ����������
		List<Point> oldcenters=kmeans.initCenters(k, players);
		List<Point> newcenters=null;
		//ѭ�����о�������
		for(int i=0;i<kmeans.repeat;i++){
			System.out.println("------------------��"+i+"�ξ��࿪ʼ--------------------");
			players=kmeans.classify(players,oldcenters);
			newcenters=kmeans.calNewCenters(players,oldcenters);
			//�������ĵ�ƫ����С����ֵ������ֹѭ��
			if(kmeans.calCenterOffSet(kmeans.mu,oldcenters,newcenters)){
				System.out.println("��"+i+"�ξ���Ľ������ս⣺"+newcenters);
				break;
			}
			//���þɵľ�������
			oldcenters.clear();
			oldcenters.addAll(newcenters);
			System.out.println("------------------��"+i+"�ξ������--------------------");
		}
		
	}
	
	
	
    
    

}
