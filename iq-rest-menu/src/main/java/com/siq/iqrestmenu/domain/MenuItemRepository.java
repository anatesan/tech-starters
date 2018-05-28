package com.siq.iqrestmenu.domain;

import com.siq.iqrestmenu.domain.MenuItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


/**
 * Created by ashok.natesan on 7/21/17.
 */

public interface MenuItemRepository extends CrudRepository<MenuItem, Long>{
    Iterable<MenuItem> findByShortNameContainingAllIgnoringCase(String shortName);
    Iterable<MenuItem> findByShortName(String shortName);
}
