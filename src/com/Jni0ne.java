package com;

import java.util.Arrays;
import java.util.concurrent.locks.ReentrantLock;

public class Jni0ne {

	// private static native void getValue();

	public  static volatile  int count  = 1;
	
	public static void main(String[] args) {
		int a[] = { 45, 2, 4, 122, 4, 32, 57, 32, 53, 677 };

		insertSort(a);
		
		int index = barryfind(a,122);
//
		for (int d : a) {
			count ++;
			System.out.println("d:" + d);
		}
		System.out.println(" a length "+a.length+"targer index :" + index+"count="+count);
	}
	

	
	
	
	
 static	int barryfind(int a[],int targer){
		int low =0; int height = a.length-1;
		while(low<=height){
			int mid = (height+low)/2;
			if(a[mid]<targer){
				low = mid+1;
			}else if(a[mid]>targer){
				height = mid-1;
			}else{ 
				return mid;
			}
		}
		return low;
	}

public static	void insertSort(int a[]) {
		for (int i = 1; i < a.length; i++) {
			if (a[i -1] > a[i]) {
				int temp = a[i];
				int j = i ;
				while (j > 0&&a[j-1] > temp ) {
					a[j]=a[j-1];
					j--;
				}
				a[j++] = temp;
			}
		}
	}

	static int getMeddle(int a[], int low, int hight) {
		int temp = a[low];
		while (low < hight) {// 寻找右边比中间值小的
			while (low < hight && a[hight] > temp) {
				hight--;
			}
			a[low] = a[hight];// 找到后放入进去
			while (low < hight && a[low] < temp) {
				low++;
			}
			a[hight] = a[low];
		}
		a[low] = temp;
		return low;
	}

	static void QuickSort(int a[], int low, int height) {
		if (low < height) {
			int meddle = getMeddle(a, low, height);
			QuickSort(a, low, meddle);
			QuickSort(a, meddle + 1, height);
		}
	}

	/**
	 * 冒泡排序 nlogn2
	 * 
	 * @param a
	 */
	static void bubbleSort(int a[]) {
		int temp = 0;
		for (int i = 0; i < a.length - 1; i++) { // 依次循环，从待排序的数组里面找进行比较找出最大的
			for (int j = 0; j < a.length - 1 - i; j++) {
				if (a[j + 1] < a[j]) {
					temp = a[j];
					a[j] = a[j + 1];
					a[j + 1] = temp;
				}
			}
		}
	}

	/**
	 * 插入排序
	 * 
	 * @param a
	 *            时间复杂度 On^2 空间复杂度O(1)
	 */
	static void InsertSort(int a[]) {
		int temp;
		// 从1~n 的无序数组中与i的有序数组进行比较
		for (int i = 1; i < a.length; i++) {
			temp = a[i];
			if (a[i - 1] > a[i]) {// 如果无序是数据比有序的少 那么在有序的数据里面找到插入的位置，然后插入
				int j = i - 1;
				while (j > 0 && a[j] > temp) {// 如果有序的数据比较无序数据大，进往后移动一位
					a[j + 1] = a[j];
					j--;
				}
				a[j + 1] = temp;// 找到插入位置放入值
			}
		}
	}

	/**
	 * 归并算法
	 * 
	 * @param a
	 * @param low
	 * @param hight
	 */
	void mergeSort(int a[], int low, int hight) {
		if (low < hight) {
			int[] arr = new int[a.length];
			int meiddle = (low + hight) / 2;
			mergeSort(a, low, meiddle);
			mergeSort(a, meiddle + 1, hight);
			merge(a, arr, low, meiddle, hight);
		}
	}

	void merge(int a[], int arr[], int low, int meiddle, int hight) {
		int i = low;
		int j = meiddle;
		int arrindex = 0;
		while (i < meiddle && j < hight) {// 对吧2个段的数据如果那个段的数据对于的数据小那么就把它排到对应位置
			if (a[i] > a[j]) {
				arr[arrindex++] = a[j++];
			} else {
				arr[arrindex++] = a[i++];
			}
		}
		while (i < meiddle) {// 如果还有数据没有派完的放入有序数组中
			arr[arrindex++] = a[i++];
		}
		while (j < hight) {// 如果还有数据没有派完的放入有序数组中
			arr[arrindex++] = a[j++];
		}
		for (i = low, arrindex = 0; low < hight; i++, arrindex++) {// 把排序数据放入数组中
			a[i] = arr[arrindex++];
		}
	}

	/**
	 * 树初始化
	 * 
	 * @param a
	 * @param length
	 */
	public static void initSort(int a[], int length) {
		boolean ischange = false;
		int hight = length - 1;
		for (int i = (hight - 1) / 2; i >= 0; i--) {
			int left = i * 2 + 1;
			int right = left + 1;
			if (left <= hight) {// 寻找子节点和自己最大的然后交换位置
				int max = left;
				if (right <= hight) {
					if (a[max] < a[right]) {
						max = right;
					}
				}
				if (a[max] > a[i]) {
					ischange = true;
					swithData(a, max, i);
				}
			}
		}
		if (ischange) {// 如果树发生改变，递归一次
			initSort(a, length);
			System.out.println("递归" + Arrays.toString(a));
		}
	}

	/**
	 * 交换2个数据共方法
	 * 
	 * @param a
	 * @param max
	 * @param index
	 */
	public static void swithData(int a[], int max, int index) {
		int temp = a[index];
		a[index] = a[max];
		a[max] = temp;
	}

	/**
	 * 树的堆排序 二叉树排序
	 * 
	 * @param a
	 */
	public static void TreeSort(int a[]) {
		initSort(a, a.length);
		System.out.print("初始化堆后：" + Arrays.toString(a));
		swithData(a, 0, a.length - 1);
		for (int length = a.length - 1; length > 1; length--) {
			int index = 0;
			while (2 * index + 1 < length) {
				if (2 * index + 2 < length) {// 如果有右子树
												// 那么就对吧2个值谁最大，如果有交换位置，那么交换位置后重新排序
					int max = index;
					if (a[2 * index + 1] > a[max]) {
						max = 2 * index + 1;
					}

					if (a[2 * index + 2] > a[max]) {
						max = 2 * index + 2;
					}
					if (max != index) {
						swithData(a, max, index);
						index = max;
					} else {
						break;// 如果相当那么就不需要
					}
				}

				if (2 * index + 1 < length && 2 * index + 2 >= length) {// 如果只有左子树
																		// 那么直接交换数据就ok
					if (a[2 * index + 1] > a[index]) {
						swithData(a, 2 * index + 1, index);
					} else {
						break;
					}
				}
			}
			swithData(a, 0, length - 1);
		}
	}

}
