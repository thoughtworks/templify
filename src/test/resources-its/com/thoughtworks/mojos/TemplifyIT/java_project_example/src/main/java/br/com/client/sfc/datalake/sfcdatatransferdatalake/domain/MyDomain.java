package br.com.client.sfc.datalake.sfcdatatransferdatalake.domain;

import java.util.List;
import br.com.client.sfc.datalake.sfcdatatransferdatalake.controllers.*;
import br.com.client.sfc.datalake.sfcdatatransferdatalake.adapters.MyAdapter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * MyDomain
 */
public class MyDomain {
    String myField;
    List<MyController> myControllers;
    MyAdapter myAdapter;


}
