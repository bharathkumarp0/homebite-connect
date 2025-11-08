package com.homebite_backend.Repo;

import com.homebite_backend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepo  extends JpaRepository<Address,Integer> {


}
