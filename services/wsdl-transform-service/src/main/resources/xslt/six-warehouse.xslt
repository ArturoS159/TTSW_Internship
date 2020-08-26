<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:ns2="http://www.intership.ttsw.com.pl/product-model"
                xmlns:ns3="http://www.intership.ttsw.com.pl/soap-six-warehouse"
                version="1.0"
                exclude-result-prefixes="ns3">

    <xsl:template match="/">
        <ns2:Products>
            <xsl:for-each select="//ns3:Product">
                <ns2:Product>
                    <id>
                        <xsl:value-of select="current()/ns3:id"/>
                    </id>
                    <name>
                        <xsl:value-of select="current()/ns3:name"/>
                    </name>
                    <image>
                        <xsl:value-of select="current()/ns3:photo"/>
                    </image>
                    <manufacturer>
                        <xsl:value-of select="current()/ns3:madeBy"/>
                    </manufacturer>
                    <warehouse>Warehouse6</warehouse>
                    <price>
                        <xsl:for-each select="current()/ns3:prices/ns3:price">
                            <xsl:sort select="ns3:date"/>
                            <xsl:if test="position()=last()">
                                <xsl:value-of select="ns3:value"/>
                            </xsl:if>
                        </xsl:for-each>
                    </price>
                    <maxAmount>
                        <xsl:value-of select="current()/ns3:quantity"/>
                    </maxAmount>
                </ns2:Product>
            </xsl:for-each>
        </ns2:Products>
    </xsl:template>
</xsl:stylesheet>