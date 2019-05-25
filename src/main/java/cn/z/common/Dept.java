package cn.z.common;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName("dept")
public class Dept {
    private Integer id;
    private String name;
    private String addr;

    public Dept() {
        System.out.println("\n     dept的构造器------");
    }

    @Override
    public String toString() {
        return "Dept{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addr='" + addr + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
