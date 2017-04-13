package com.arun.test3;

public class TestCase3 {

	int x;
	int y;
	int z;


	public static void main(String[] args) {

	}

	
	
	@Override
	public String toString() {
		return "TestCase3 [x=" + x + ", y=" + y + ", z=" + z + "]";
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCase3 other = (TestCase3) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}



	public TestCase3(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}


	public TestCase3(){}
}
