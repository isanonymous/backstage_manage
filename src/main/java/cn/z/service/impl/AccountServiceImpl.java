package cn.z.service.impl;

import cn.z.entity.Account;
import cn.z.entity.SkillType;
import cn.z.mapper.AccountMapper;
import cn.z.mapper.SkillTypeMapper;
import cn.z.service.AccountService;
import cn.z.service.SkillTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper,Account> implements AccountService {
}
