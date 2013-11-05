package edu.sjsu.cmpe.procurement.util;

import edu.sjsu.cmpe.procurement.config.ProcurementServiceConfiguration;

public class ProcurementConfig {
	private static ProcurementServiceConfiguration procurementConfig;

	public static ProcurementServiceConfiguration getProcurementConfig() {
		return procurementConfig;
	}

	public static void setProcurementConfig(
			ProcurementServiceConfiguration procurementConfig) {
		ProcurementConfig.procurementConfig = procurementConfig;
	}
}
