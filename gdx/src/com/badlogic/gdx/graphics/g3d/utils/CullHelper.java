/*
 * Created on Jun 20, 2023
 */

package com.badlogic.gdx.graphics.g3d.utils;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.model.*;
import com.badlogic.gdx.math.*;

/** @author dar */
public class CullHelper {
	private final Vector3 rCenter = new Vector3();
	private final Vector3 rScale = new Vector3();
	private final Vector3 rRadius = new Vector3();

	/** Check if the given renderable is in the camera frustum. Note: the given mesh renderable needs to be updated by calling
	 * {@link MeshPart#update()}
	 * 
	 * @param cam the src camera
	 * @param r the renderable to check
	 * @return true if the renderable needs to be culled */
	public boolean canCullRenderable (final Camera cam, final Renderable r) {
		// skip not updated mesh
		if (r.meshPart.radius == -1) return false;

		// get renderable scale
		r.worldTransform.getScale(rScale);

		// mult the node center to world transform
		rCenter.set(r.meshPart.center);
		rCenter.mul(r.worldTransform);

		// compute radius by scaling with world scale
		rRadius.set(rScale).scl(r.meshPart.radius);
		float radius = rRadius.x;
		radius = Math.max(radius, rRadius.y);
		radius = Math.max(radius, rRadius.z);

		// check if this sphere is in camera frustum
		return !cam.frustum.sphereInFrustum(rCenter, radius);
	}
}
