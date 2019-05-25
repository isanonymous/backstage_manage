package cn.z;

public interface State {
  int SUCCESS=1,FAIL=0;
}

enum MyEnum {
  ONE(1,'1'),TWO(2,'2'),;

  MyEnum(int i, char c) {
  }
}