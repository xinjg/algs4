public class Percolation {
	
	private WeightedQuickUnionUF un;
	private int N;
	private int top,bottom;
	private boolean[] isOpen;
	// create N-by-N grid, with all sites blocked
	public Percolation(int N) {
		//N^2+1 is the  victual top site,N^2+2 is the victual bottom site
		int  cnt = N*N+2;
		isOpen=new boolean[cnt];
		un=new WeightedQuickUnionUF(cnt);
		this.top=cnt-1;
		this.bottom=cnt-2;
		isOpen[top]=true;
		isOpen[bottom]=true;
		for(int i=0;i<N;i++){
			un.union(top, i);
			un.union(bottom, bottom-1-i);
		}
		this.N=N;
	}
	private void check(int i,int j)throws java.lang.IndexOutOfBoundsException{
		if(i<0||i>=N||j<0||j>=N)
			throw new IndexOutOfBoundsException("非法参数 "+ i +"-"+j);
	}
	// site up to (row i, column j), return -1 if x  top
	private int up(int i, int j){
		check(i,j);
		int site=-1;
		if(i>0&&i<N
				&&j>=0&&j<N)
			site=this.site(i-1, j);
		return site;
	}
	// site down  to (row i, column j), return -1 if x bottom
	private int down(int i, int j){
		check(i,j);
		int site=-1;
		if(i>=0&&i<N-1
				&&j>=0&&j<N){
			site=this.site(i+1, j);
		}
		return site;
	}
	//site left to (row i, column j),return -1 if has no left site
	private int left(int i, int j){
		check(i,j);
		int site=1;
		if(i>=0&&i<N
				&&j>0&&j<N){
			site=this.site(i, j-1);
		}
		return site;
	}
	//site right to (row i, column j),return -1 if has no right site
	private int right(int i, int j){
		check(i,j);
		int site=1;
		if(i>=0&&i<N
				&&j>=0&&j<N-1){
			site=this.site(i, j-1);
		}
		return site;
	}
	//
	private int site(int i, int j){
		check(i,j);
		return i*N+j;
	}
	
	// open site (row i, column j) if it is not open already
	public void open(int i, int j) {
		if(!isOpen(i, j)){
			int site=this.site(i, j);
			isOpen[site]=true;
			int up=this.up(i, j);
			if(isOpen[up]&&up!=-1){
				un.union(site, up);
			}
			int down=this.down(i, j);
			if(isOpen[down]&&down!=-1){
				un.union(site, down);
			}
			int left=this.left(i, j);
			if(isOpen[left]&&left!=-1){
				un.union(site, left);
			}
			int right=this.right(i, j);
			if(isOpen[right]&&right!=-1){
				un.union(site, right);
			}
		}
	}

	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		return isOpen[this.site(i, j)];
	}

	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		int site=this.site(i, j);
		return un.connected(top, site)&&un.connected(bottom, site);
	}

	// does the system percolate?
	public boolean percolates() {
		return un.connected(top, bottom);
	}

	public static void main(String[] args) {
	} // test client (optional)
}
