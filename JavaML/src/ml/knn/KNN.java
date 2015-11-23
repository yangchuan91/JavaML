package ml.knn;

import java.util.Collections;
import java.util.List;
/**
 * KNN算法主体
 * 
 * @author yangchuan
 *
 */
public class KNN {

	// 计算两个点之间的距离,计算待测数据和样本数据之间的距离
	public double calDistance(Point point, Point sample) {
		double dis = (point.getX() - sample.getX()) * (point.getX() - sample.getX())
				+ (point.getY() - sample.getY()) * (point.getY() - sample.getY());
		return Math.sqrt(dis);
	}
	/**
	 * 循环计算样本数据中每个点到待测数据的距离，获取距离待测数据最近的前K个点的集合
	 * @param point  待测数据
	 * @param dataSet 样本数据
	 * @param k 最近距离点的个数
	 * @return KNNlist 最近距离点的集合
	 */
	public List<Point> calKNNPointList(Point point,List<Point> dataSet,int k){
		System.out.println("初始的样本数据为："+dataSet);
		//循环计算样本数据中每个点到待测数据的距离
		for(Point oriData:dataSet){
			oriData.setDistance(calDistance(point,oriData));
		}
		//对样本数据进行排序，并且后期前K个点放入KNNlist中
		Collections.sort(dataSet);
		System.out.println("排序后的样本数据为："+dataSet);
		//获取最近的K个元素并且返回
		List<Point> KNNlist =dataSet.subList(0, k);
		System.out.println("KNNlist 最近距离点的集合数据为："+KNNlist);
		return KNNlist;
	}
	/**
	 * 根据距离待测点最近的点的集合，来判断待测点属于哪个分类
	 * @param point
	 * @param KNNlist
	 * @return point
	 */
	public int calKNNPointClusterId(Point point,List<Point> KNNlist){
		int[] countArray=new int[]{0,0,0,0};
		//对距离待测点最近的点的集合KNNlist的分类进行计数，数目最多的类别，就是待测点的类别
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
		System.out.println("countArray分别是："+countArray[0]+","+countArray[1]+","+countArray[2]+","+countArray[3]);
		//获取分类Id
		int clusterId=getMaxCountClusterId(countArray);
		return clusterId;
	}
	/**
	 * 获取数值最大的索引值+1(索引值+1就是ClusterId)
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
		//用于产生待测分类数据集合
		String oriPath="C:/Users/hp/git/JavaML/JavaML/file/knnOri.txt";
		String testPath="C:/Users/hp/git/JavaML/JavaML/file/knnTest.txt";
		//获取样本数据
		List<Point> pointList=GenOriData.getOriKNNData(oriPath);
		//获取待分类数据
		List<Point> callist=GenOriData.getOriKNNData(testPath);
		KNN knn=new KNN();
		int length=callist.size();
		for(int i=0;i<length;i++){
			Point point=callist.get(i);
			System.out.println("---------------------------------------------------------------------");
			System.out.println("第"+(i+1)+"个点KNN分类开始："+point);
			System.out.println("第"+(i+1)+"个点KNN分类结束："+knn.knnAlgotithm(point, pointList, 5));
			System.out.println("---------------------------------------------------------------------");
		}
	}

}
