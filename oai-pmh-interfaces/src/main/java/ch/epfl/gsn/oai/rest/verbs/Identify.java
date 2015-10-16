package ch.epfl.gsn.oai.rest.verbs;

import ch.epfl.gsn.oai.interfaces.RecordAccessService;
import ch.epfl.gsn.oai.interfaces.RepositoryIdentity;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

/**
 * Created by kryvych on 04/09/15.
 */
@Named
@Scope("prototype")
public class Identify {

    protected static final Logger logger = LoggerFactory.getLogger(Identify.class);

    private final TemplateHelper templateHelper;

    private final RepositoryIdentity repositoryIdentity;

    private final RecordAccessService recordAccessService;

    @Inject
    public Identify(TemplateHelper templateHelper, RepositoryIdentity repositoryIdentity, RecordAccessService recordAccessService) {
        this.templateHelper = templateHelper;
        this.repositoryIdentity = repositoryIdentity;
        this.recordAccessService = recordAccessService;
    }


    public String getResponse() {
        Map<String, String> parameters = templateHelper.getCommonParameters();
        parameters.put("request", repositoryIdentity.baseURL());
        parameters.put("repositoryName", repositoryIdentity.repositoryName());
        parameters.put("baseURL", repositoryIdentity.baseURL());
        parameters.put("adminEmail", repositoryIdentity.adminEmail());
        parameters.put("deletedRecord", repositoryIdentity.deletedRecords());
        parameters.put("granularity", repositoryIdentity.granularity());

        parameters.put("earliestDatestamp", templateHelper.formatDate(recordAccessService.getEarliestDatestamp()));
        return templateHelper.fillTemplate("identify.template", parameters);

    }

}
