package ml.knn;

import java.util.Collections;
import java.util.List;
/**
 * KNN�㷨����
 * 
 * @author yangchuan
 *
 */
public class KNN {

	// ����������֮��ľ���,����������ݺ���������֮��ľ���
	public double calDistance(Point point, Point sample) {
		double dis = (point.getX() - sample.getX()) * (point.getX() - sample.getX())
				+ (point.getY() - sample.getY()) * (point.getY() - sample.getY());
		return Math.sqrt(dis);
	}
	/**
	 * ѭ����������������ÿ���㵽�������ݵľ��룬��ȡ����������������ǰK����ļ���
	 * @param point  ��������
	 * @param dataSet ��������
	 * @param k ��������ĸ���
	 * @return KNNlist ��������ļ���
	 */
	public List<Point> calKNNPointList(Point point,List<Point> dataSet,int k){
		System.out.println("��ʼ����������Ϊ��"+dataSet);
		//ѭ����������������ÿ���㵽�������ݵľ���
		for(Point oriData:dataSet){
			oriData.setDistance(calDistance(point,oriData));
		}
		//���������ݽ������򣬲��Һ���ǰK�������KNNlist��
		Collections.sort(dataSet);
		System.out.println("��������������Ϊ��"+dataSet);
		//��ȡ�����K��Ԫ�ز��ҷ���
		List<Point> KNNlist =dataSet.subList(0, k);
		System.out.println("KNNlist ��������ļ�������Ϊ��"+KNNlist);
		return KNNlist;
	}
	/**
	 * ���ݾ�����������ĵ�ļ��ϣ����жϴ���������ĸ�����
	 * @param point
	 * @param KNNlist
	 * @return point
	 */
	public int calKNNPointClusterId(Point point,List<Point> KNNlist){
		int[] countArray=new int[]{0,0,0,0};
		//�Ծ�����������ĵ�ļ���KNNlist�ķ�����м�������Ŀ������𣬾��Ǵ��������
		for(Point oriData:KNNlist){
			if(oriData.getClusterId()==1){
				countArray[0]+=1;
			}else if(oriData.getClusterId()==2){
				countArray[1]+=1;
			}else if(oriData.getClusterId()==3){
				countArray[2]+=1;
			}else{
				countArray[3]+=1;
			}
		}
		System.out.println("countArray�ֱ��ǣ�"+countArray[0]+","+countArray[1]+","+countArray[2]+","+countArray[3]);
		//��ȡ����Id
		int clusterId=getMaxCountClusterId(countArray);
		return clusterId;
	}
	/**
	 * ��ȡ��ֵ��������ֵ+1(����ֵ+1����ClusterId)
	 * @param countArray
	 * @return
	 */
	public int getMaxCountClusterId(int[] countArray){
		if(countArray==null||countArray.length==0){
			return 0;
		}
		int max=countArray[0];
		int index=0;
		for(int i=1;i<countArray.length;i++){
			if(countArray[i]>max){
				max= countArray[i];
				index=i;
			}
		}
		return index+1;
	}
	public Point knnAlgotithm(Point point,List<Point> dataSet,int k){
		List<Point> KNNlist=calKNNPointList(point, dataSet, k);
		int clusterId=calKNNPointClusterId(point,KNNlist);
		point.setClusterId(clusterId);
		return point;
	}
	public static void main(String[] args) {
		//���ڲ�������������ݼ���
		String oriPath="C:/Users/hp/git/JavaML/JavaML/file/knnOri.txt";
		String testPath="C:/Users/hp/git/JavaML/JavaML/file/knnTest.txt";
		//��ȡ��������
		List<Point> pointList=GenOriData.getOriKNNData(oriPath);
		//��ȡ����������
		List<Point> callist=GenOriData.getOriKNNData(testPath);
		KNN knn=new KNN();
		int length=callist.size();
		for(int i=0;i<length;i++){
			Point point=callist.get(i);
			System.out.println("---------------------------------------------------------------------");
			System.out.println("��"+(i+1)+"����KNN���࿪ʼ��"+point);
			System.out.println("��"+(i+1)+"����KNN���������"+knn.knnAlgotithm(point, pointList, 5));
			System.out.println("---------------------------------------------------------------------");
		}
	}

}
