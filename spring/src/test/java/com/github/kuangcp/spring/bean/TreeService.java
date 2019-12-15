package com.github.kuangcp.spring.bean;

import com.github.kuangcp.spring.beans.annotation.Autowired;
import com.github.kuangcp.spring.stereotype.Component;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author https://github.com/kuangcp on 2019-12-01 22:55
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component(value = "treeService")
public class TreeService {

  @Autowired
  private TreeDao treeDao;

  @Autowired
  private String version;
}
