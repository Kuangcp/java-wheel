package com.github.kuangcp.spring.bean.loop;

import com.github.kuangcp.spring.beans.annotation.Autowired;
import com.github.kuangcp.spring.stereotype.Component;
import lombok.Data;

/**
 * @author https://github.com/kuangcp on 2019-12-22 19:27
 */
@Data
@Component
public class AvatarService {

  @Autowired
  private PlayerService playerService;
}
