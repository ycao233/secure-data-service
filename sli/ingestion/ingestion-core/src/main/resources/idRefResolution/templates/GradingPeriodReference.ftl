<GradingPeriodReference>
    <GradingPeriodIdentity>

        <#if (GradingPeriod.GradingPeriodIdentity.GradingPeriod[0])??>
            <GradingPeriod>${GradingPeriod.GradingPeriodIdentity.GradingPeriod}</GradingPeriod>
        </#if>

        <#if (GradingPeriod.GradingPeriodIdentity.SchoolYear[0])??>
            <SchoolYear>${GradingPeriod.GradingPeriodIdentity.SchoolYear}</SchoolYear>
        </#if >

        <#if (GradingPeriod.GradingPeriodIdentity.StateOrganizationId[0])??>
            <StateOrganizationId>${GradingPeriod.GradingPeriodIdentity.StateOrganizationId}</StateOrganizationId>
        </#if>

    </GradingPeriodIdentity>
</GradingPeriodReference>
